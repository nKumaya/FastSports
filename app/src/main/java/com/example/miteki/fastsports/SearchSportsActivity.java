package com.example.miteki.fastsports;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.miteki.fastsports.common.data.fixtures.MessageFixtures;
import com.example.miteki.fastsports.model.CardViewModel;
import com.example.miteki.fastsports.model.Message;
import com.example.miteki.fastsports.model.User;
import com.example.miteki.fastsports.utils.AppUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.api.AIServiceException;
import ai.api.RequestExtras;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.GsonFactory;
import ai.api.model.AIContext;
import ai.api.model.AIError;
import ai.api.model.AIEvent;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import ai.api.model.Metadata;
import ai.api.model.ResponseMessage;
import ai.api.model.Result;
import ai.api.model.Status;
import ai.api.ui.AIDialog;

public class SearchSportsActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        AIDialog.AIDialogListener{

    private static final String TAG = AIDialogActivity.class.getName();
    private AIDialog aiDialog;
    private AIDataService aiDataService;

    private Gson gson = GsonFactory.getGson();

    public static void open(Context context) {
        context.startActivity(new Intent(context, HogeActivity.class));
    }

    private MessagesList messagesList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sports);

        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);


        final AIConfiguration config = new AIConfiguration(Config.ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.Japanese,
                AIConfiguration.RecognitionEngine.System);

        aiDialog = new AIDialog(this, config);
        aiDialog.setResultsListener(this);
        aiDataService = new AIDataService(this, config);
    }

    @Override
    protected void onStart(){
        super.onStart();
        messagesAdapter.addToStart(
                MessageFixtures.getBotTextMessage("今日はどうしますか？"), true);
    }


    @Override
    public void onAddAttachments() {
        aiDialog.showAndListen();
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        sendRequest(input.toString());
        return true;
    }

    private void initAdapter() {
        // custom
        MessageHolders holders = new MessageHolders();
        holders.registerContentType((byte) 1, CustomViewHolder.class, R.layout.card_list1, R.layout.event_card, new MessageHolders.ContentChecker() {
            @Override
            public boolean hasContentFor(IMessage message, byte type) {
                return true;
            }
        });
        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, holders, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        super.messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<IMessage>() {
                    @Override
                    public void onMessageViewClick(View view, IMessage message) {
                        AppUtils.showToast(SearchSportsActivity.this,
                                message.getUser().getName() + " avatar click",
                                false);
                    }
                });
        this.messagesList.setAdapter(super.messagesAdapter);
    }



    private void sendRequest(String inputQuery) {

        final String queryString = inputQuery;

        if (TextUtils.isEmpty(queryString)) {
            return;
        }

        final AsyncTask<String, Void, AIResponse> task = new AsyncTask<String, Void, AIResponse>() {

            private AIError aiError;

            @Override
            protected AIResponse doInBackground(final String... params) {
                final AIRequest request = new AIRequest();
                String query = params[0];

                if (!TextUtils.isEmpty(query))
                    request.setQuery(query);
                RequestExtras requestExtras = null;

                try {
                    return aiDataService.request(request, requestExtras);
                } catch (final AIServiceException e) {
                    aiError = new AIError(e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(final AIResponse response) {
                if (response != null) {
                    onResult(response);
                } else {
                    onError(aiError);
                }
            }
        };

        task.execute(queryString);
    }

    @Override
    public void onResult(final AIResponse response) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                final Status status = response.getStatus();
                final Result result = response.getResult();
                messagesAdapter.addToStart(
                        MessageFixtures.getTextMessage(result.getResolvedQuery()), true);

                String gsonData = gson.toJson(response.getResult().getFulfillment());

                boolean isConversationFinished = false;
                try {
                    JSONObject json = new JSONObject(gsonData);
                    JSONArray rows = json.getJSONArray("messages");
                    for(int i=0; i<rows.length(); i++){
                        JSONObject row = rows.getJSONObject(i);
                        JSONArray speechArray = row.getJSONArray("speech");
                        for(int j=0;j<speechArray.length();j++){
                            String speech =  speechArray.get(j).toString();
                            messagesAdapter.addToStart(
                                    MessageFixtures.getBotTextMessage(speech), true);
                            isConversationFinished = checkConversationFlag(speech);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                if(isConversationFinished){
                    User hogeUser = new User("1", "hoge", "", true);
                    messagesAdapter.addToStart(new CardViewModel("1", hogeUser, "aaaaa", new Date()), true);
                    messagesAdapter.addToStart(
                            MessageFixtures.getBotTextMessage("こういうのはどうでしょうか"), true);
                }

                final String speech = result.getFulfillment().getSpeech();
                List<ResponseMessage> responseMessages = result.getFulfillment().getMessages();
                for (ResponseMessage message:
                        responseMessages) {
                    Class<?> class1 = message.getClass();
                }

                final Metadata metadata = result.getMetadata();
                if (metadata != null) {
                }
            }
        });
    }

    @Override
    public void onError(AIError error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    @Override
    public void onCancelled() {
    }

    @Override
    protected void onPause() {
        if (aiDialog != null) {
            aiDialog.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (aiDialog != null) {
            aiDialog.resume();
        }
        super.onResume();
    }

    public void buttonListenOnClick(final View view) {
        aiDialog.showAndListen();
    }

    public void moveChatActivityOnClick(final View view) {
        Intent intent = new Intent(SearchSportsActivity.this, EventDetailActivity.class);
        startActivity(intent);
    }

    private boolean checkConversationFlag(String botMessage){
        if(botMessage.equals("少々お待ちください"))
            return true;
        else
            return false;
    }

}

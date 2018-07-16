package com.example.miteki.fastsports;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.miteki.fastsports.common.data.fixtures.MessageFixtures;
import com.example.miteki.fastsports.model.CardViewModel;
import com.example.miteki.fastsports.model.CustomButtonViewModel;
import com.example.miteki.fastsports.model.User;
import com.example.miteki.fastsports.utils.AppUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.api.AIServiceException;
import ai.api.RequestExtras;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.GsonFactory;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.ResponseMessage;
import ai.api.model.Result;
import ai.api.model.Status;
import ai.api.ui.AIDialog;

public class DemoNoticeActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        AIDialog.AIDialogListener{

    private MessagesList messagesList;
    private Gson gson = GsonFactory.getGson();
    private AIDialog aiDialog;
    private AIDataService aiDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_notice);

        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();
        remindMessages();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        final AIConfiguration config = new AIConfiguration(Config.ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.Japanese,
                AIConfiguration.RecognitionEngine.System);
        aiDialog = new AIDialog(this, config);
        aiDialog.setResultsListener(this);
        aiDataService = new AIDataService(this, config);

    }

    private void initAdapter() {
        // custom
        MessageHolders holders = new MessageHolders();
        holders.registerContentType((byte) 1, CustomButtonView.class, R.layout.ask_bot_button, R.layout.ask_bot_button, new MessageHolders.ContentChecker() {
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
                        AppUtils.showToast(DemoNoticeActivity.this,
                                message.getUser().getName() + " avatar click",
                                false);
                    }
                });
        this.messagesList.setAdapter(super.messagesAdapter);
    }

    private void remindMessages(){
        messagesAdapter.addToStart(MessageFixtures.getBotTextMessage("いよいよイベントは明日です！"), true);
        messagesAdapter.addToStart(MessageFixtures.getBotTextMessage("何かわからないことがあれば聞いてください"), true);
        User hogeUser = new User("1", "hoge", "", true);
        messagesAdapter.addToStart(new CustomButtonViewModel("1", hogeUser, "aaaaa", new Date()), true);
    }


    @Override
    public void onAddAttachments() {
        super.messagesAdapter.addToStart(
                MessageFixtures.getImageMessage(), true);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        return true;
    }

    public void askFee(final View view) {
        sendRequest("料金を教えて");
    }

    private void sendRequest(String inputQuery) {

        final String queryString = inputQuery;

        if (TextUtils.isEmpty(queryString)) {
//            onError(new AIError(getString(R.string.non_empty_query)));
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
                // this is example how to get different parts of result object
                final Status status = response.getStatus();

                final Result result = response.getResult();
                messagesAdapter.addToStart(
                        MessageFixtures.getTextMessage(result.getResolvedQuery()), true);


                String gsonData = gson.toJson(response.getResult().getFulfillment());

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
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
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

                final HashMap<String, JsonElement> params = result.getParameters();
                if (params != null && !params.isEmpty()) {
                    for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                    }
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
}

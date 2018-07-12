package com.example.miteki.fastsports;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.miteki.fastsports.common.data.fixtures.MessageFixtures;
import com.example.miteki.fastsports.model.Message;
import com.example.miteki.fastsports.utils.AppUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.HashMap;
import java.util.Map;

import ai.api.android.AIConfiguration;
import ai.api.android.GsonFactory;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.Status;
import ai.api.ui.AIDialog;

public class HogeActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        AIDialog.AIDialogListener{

    private static final String TAG = AIDialogActivity.class.getName();
    private AIDialog aiDialog;
    private Gson gson = GsonFactory.getGson();

    public static void open(Context context) {
        context.startActivity(new Intent(context, HogeActivity.class));
    }

    private MessagesList messagesList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoge);

        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);

        final AIConfiguration config = new AIConfiguration(Config.ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.Japanese,
                AIConfiguration.RecognitionEngine.System);

        aiDialog = new AIDialog(this, config);
        aiDialog.setResultsListener(this);
    }


    @Override
    public void onAddAttachments() {
        super.messagesAdapter.addToStart(
                MessageFixtures.getImageMessage(), true);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        super.messagesAdapter.addToStart(
                MessageFixtures.getTextMessage(input.toString()), true);
        super.messagesAdapter.addToStart(
                MessageFixtures.getBotTextMessage("もう一度お願いします。"), true);
        return true;
    }

    private void initAdapter() {
        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        super.messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<Message>() {
                    @Override
                    public void onMessageViewClick(View view, Message message) {
                        AppUtils.showToast(HogeActivity.this,
                                message.getUser().getName() + " avatar click",
                                false);
                    }
                });
        this.messagesList.setAdapter(super.messagesAdapter);
    }

    @Override
    public void onResult(final AIResponse response) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
                Log.d(TAG, "onResult");

//                resultTextView.setText(gson.toJson(response));

                Log.i(TAG, "Received success response");

                // this is example how to get different parts of result object
                final Status status = response.getStatus();
                Log.i(TAG, "Status code: " + status.getCode());
                Log.i(TAG, "Status type: " + status.getErrorType());

                final Result result = response.getResult();
                Log.i(TAG, "Resolved query: " + result.getResolvedQuery());


                Log.i(TAG, "Action: " + result.getAction());
                final String speech = result.getFulfillment().getSpeech();
                Log.i(TAG, "Speech: " + speech);
//                TTS.speak(speech);

                final Metadata metadata = result.getMetadata();
                if (metadata != null) {
                    Log.i(TAG, "Intent id: " + metadata.getIntentId());
                    Log.i(TAG, "Intent name: " + metadata.getIntentName());
                }

                final HashMap<String, JsonElement> params = result.getParameters();
                if (params != null && !params.isEmpty()) {
                    Log.i(TAG, "Parameters: ");
                    for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                        Log.i(TAG, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
                    }
                }
//            }

//        });

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
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                resultTextView.setText("");
//            }
//        });
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
}

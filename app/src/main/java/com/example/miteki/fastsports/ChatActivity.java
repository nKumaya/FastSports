package com.example.miteki.fastsports;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.miteki.fastsports.common.data.fixtures.MessageFixtures;
import com.example.miteki.fastsports.model.Message;
import com.example.miteki.fastsports.utils.AppUtils;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public abstract class ChatActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener{

    public static void open(Context context) {
        context.startActivity(new Intent(context, HogeActivity.class));
    }

    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public void onAddAttachments() {
    }

    @Override
    public boolean onSubmit(CharSequence input) {

        return true;
    }

    private void initAdapter() {
        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        super.messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<IMessage>() {
                    @Override
                    public void onMessageViewClick(View view, IMessage message) {
                        AppUtils.showToast(ChatActivity.this,
                                message.getUser().getName() + " avatar click",
                                false);
                    }
                });
        this.messagesList.setAdapter(super.messagesAdapter);
    }
}

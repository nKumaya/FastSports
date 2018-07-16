package com.example.miteki.fastsports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.miteki.fastsports.common.data.fixtures.MessageFixtures;
import com.example.miteki.fastsports.model.Message;
import com.example.miteki.fastsports.utils.AppUtils;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.miteki.fastsports.common.data.fixtures.MessageFixtures.getImageMessage;
import static com.example.miteki.fastsports.common.data.fixtures.MessageFixtures.getTextMessage;

public class DemoGroupChatActivity extends AppCompatActivity
        implements MessagesListAdapter.SelectionListener,
        MessagesListAdapter.OnLoadMoreListener{
    private ImageLoader imageLoader;
    private MessagesListAdapter<Message> messagesAdapter;
    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_group_chat);

        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(DemoGroupChatActivity.this).load(url).into(imageView);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.messagesList = (MessagesList) findViewById(R.id.messagesList);

        initActivity();
        messagesAdapter.addToStart(MessageFixtures.getGroupMessage("Fuga3さんが参加しました","0", 0), true);
        messagesAdapter.addToStart(MessageFixtures.getGroupMessage("参加してくれてどうもです！","2", 2), true);
        messagesAdapter.addToStart(MessageFixtures.getGroupMessage("経験者中心でやれればと思います！詳細みてくださいね〜","2", 2), true);
        messagesAdapter.addToStart(MessageFixtures.getGroupMessage("初参加です！大学時代やってました〜よろしくです","3", 3), true);
        messagesAdapter.addToStart(MessageFixtures.getGroupMessage("よろしく！","2", 2), true);
        messagesAdapter.addToStart(MessageFixtures.getGroupMessage("よろしくお願いします〜","3", 3), true);
        messagesAdapter.addToStart(MessageFixtures.getGroupMessage("Hoge3さんが参加しました","0", 0), true);
    }

    private void initActivity(){
        messagesAdapter = new MessagesListAdapter<>("1", imageLoader);
//        messagesAdapter.enableSelectionMode(this);
//        messagesAdapter.setLoadMoreListener(this);
        this.messagesList.setAdapter(messagesAdapter);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {

    }

    @Override
    public void onSelectionChanged(int count) {

    }

//    private ArrayList<Message> getMessages(Date startDate) {
//        ArrayList<Message> messages = new ArrayList<>();
//        for (int i = 0; i < 10/*days count*/; i++) {
//            int countPerDay = rnd.nextInt(5) + 1;
//
//            for (int j = 0; j < countPerDay; j++) {
//                Message message;
//                if (i % 2 == 0 && j % 3 == 0) {
//                    message = getImageMessage();
//                } else {
//                    message = getTextMessage();
//                }
//
//                Calendar calendar = Calendar.getInstance();
//                if (startDate != null) calendar.setTime(startDate);
//                calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1));
//
//                message.setCreatedAt(calendar.getTime());
//                messages.add(message);
//            }
//        }
//        return messages;
//    }
}

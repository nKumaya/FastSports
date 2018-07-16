package com.example.miteki.fastsports.common.data.fixtures;

import com.example.miteki.fastsports.model.Message;
import com.example.miteki.fastsports.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public final class MessageFixtures extends FixturesData{
    private MessageFixtures() {
        throw new AssertionError();
    }

    public static Message getImageMessage() {
        Message message = new Message(getRandomId(), getUser(), null);
        message.setImage(new Message.Image(getRandomImage()));
        return message;
    }

    public static Message getVoiceMessage() {
        Message message = new Message(getRandomId(), getUser(), null);
        message.setVoice(new Message.Voice("http://example.com", rnd.nextInt(200) + 30));
        return message;
    }

    public static Message getTextMessage() {
        return getTextMessage(getRandomMessage());
    }

    public static Message getTextMessage(String text) {
        return new Message(getRandomId(), getUser(), text);
    }

    public static Message getGroupMessage(String text, String strID, int id) {
        return new Message(getRandomId(), getGroupUser(strID, id), text);
    }

    public static Message getBotTextMessage() {
        return getBotTextMessage(getWelcomBotMessage());

    }

    public static Message getBotTextMessage(String text) {
        return new Message(getRandomId(), getBot(), text);
    }

    public static ArrayList<Message> getMessages(Date startDate) {
        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10/*days count*/; i++) {
            int countPerDay = rnd.nextInt(5) + 1;

            for (int j = 0; j < countPerDay; j++) {
                Message message;
                if (i % 2 == 0 && j % 3 == 0) {
                    message = getImageMessage();
                } else {
                    message = getTextMessage();
                }

                Calendar calendar = Calendar.getInstance();
                if (startDate != null) calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1));

                message.setCreatedAt(calendar.getTime());
                messages.add(message);
            }
        }
        return messages;
    }

    private static User getUser() {
//        boolean even = rnd.nextBoolean();
        return new User("0", names.get(0), avatars.get(0), true);
//        return new User(
//                even ? "0" : "1",
//                even ? names.get(0) : names.get(1),
//                even ? avatars.get(0) : avatars.get(1),
//                true);
    }

    private static User getGroupUser(String strID, int id){
        return new User(strID, names.get(id), avatars.get(id), true);

    }

    private static User getBot() {
        return new User("1", names.get(0), avatars.get(0), true);
    }
}

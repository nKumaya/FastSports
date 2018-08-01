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

    public static Message getTextMessage(String text) {
        return new Message(getRandomId(), getUser(), text);
    }

    public static Message getGroupMessage(String text, String strID, int id) {
        return new Message(getRandomId(), getGroupUser(strID, id), text);
    }

    public static Message getBotTextMessage(String text) {
        return new Message(getRandomId(), getBot(), text);
    }

    public static Message getBotWelcomeMessage() {
        return new Message(getRandomId(), getBot(), welcomeMessages.get(rnd.nextInt(welcomeMessages.size())));
    }

    private static User getUser() {
        return new User("0", names.get(0), avatars.get(0), true);
    }

    private static User getGroupUser(String strID, int id){
        return new User(strID, names.get(id), avatars.get(id), true);
    }

    private static User getBot() {
        return new User("1", names.get(0), avatars.get(0), true);
    }
}

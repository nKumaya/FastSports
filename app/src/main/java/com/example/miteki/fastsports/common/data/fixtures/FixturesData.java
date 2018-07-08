package com.example.miteki.fastsports.common.data.fixtures;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.UUID;

abstract class FixturesData {
    static SecureRandom rnd = new SecureRandom();

    static ArrayList<String> avatars = new ArrayList<String>() {
        {
            add("http://i.imgur.com/pv1tBmT.png");
            add("http://i.imgur.com/pv1tBmT.png");

        }
    };

    static final ArrayList<String> groupChatImages = new ArrayList<String>() {
        {
            add("http://i.imgur.com/hRShCT3.png");

        }
    };

    static final ArrayList<String> groupChatTitles = new ArrayList<String>() {
        {
            add("ゆるくフットサル");
        }
    };

    static final ArrayList<String> names = new ArrayList<String>() {
        {
            add("R2D2");
            add("hoge");
        }
    };

    static final ArrayList<String> messages = new ArrayList<String>() {
        {
            add("アクティビティを探したい時は、「アクティビティを探して」と話しかけてくださいね！");
        }
    };

    static final ArrayList<String> images = new ArrayList<String>() {
        {
            add("https://habrastorage.org/getpro/habr/post_images/e4b/067/b17/e4b067b17a3e414083f7420351db272b.jpg");
        }
    };

    static String getRandomId() {
        return Long.toString(UUID.randomUUID().getLeastSignificantBits());
    }

    static String getRandomAvatar() {
        return avatars.get(rnd.nextInt(avatars.size()));
    }

    static String getRandomGroupChatImage() {
        return groupChatImages.get(rnd.nextInt(groupChatImages.size()));
    }

    static String getRandomGroupChatTitle() {
        return groupChatTitles.get(rnd.nextInt(groupChatTitles.size()));
    }

    static String getRandomName() {
        return names.get(rnd.nextInt(names.size()));
    }

    static String getRandomMessage() {
        return messages.get(rnd.nextInt(messages.size()));
    }

    static String getWelcomBotMessage() {
        return messages.get(0);
    }

    static String getRandomImage() {
        return images.get(rnd.nextInt(images.size()));
    }

    static boolean getRandomBoolean() {
        return rnd.nextBoolean();
    }
}
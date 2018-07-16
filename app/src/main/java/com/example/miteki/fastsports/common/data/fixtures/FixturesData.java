package com.example.miteki.fastsports.common.data.fixtures;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.UUID;

abstract class FixturesData {
    static SecureRandom rnd = new SecureRandom();

    static ArrayList<String> avatars = new ArrayList<String>() {
        {
            add("https://dl.dropboxusercontent.com/s/sk5k0njazhj7wa8/fastsports_icon.png"); // bot
            add("https://dl.dropboxusercontent.com/s/faaqdot4d9hz7u5/avatar_img1.jpg"); // user
            add("https://dl.dropboxusercontent.com/s/faaqdot4d9hz7u5/avatar_img1.jpg"); // user1
            add("https://dl.dropboxusercontent.com/s/x74t080ftw9mx1k/avator_img2.jpg"); // user2
            add("https://dl.dropboxusercontent.com/s/dla4ymrg789qcpx/footsall1.png");
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
            add("fuga");
            add("piyo");
            add("foo");
            add("foo");
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

    static final ArrayList<String> demoChatMessages = new ArrayList<String>(){
        {
            add("経験者中心でやれればと思います！詳細みてくださいね〜");
            add("初参加です！大学時代やってました〜よろしくです");
            add("よろしくお願いします！");
            add("よろしく〜！");
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

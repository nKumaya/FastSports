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
        }
    };

    static final ArrayList<String> welcomeMessages = new ArrayList<String>(){
        {
            add("今日はどうしますか？");
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

    static String getWelcomBotMessage() {
        return welcomeMessages.get(rnd.nextInt(welcomeMessages.size()));
    }

    static String getRandomImage() {
        return images.get(rnd.nextInt(images.size()));
    }
}

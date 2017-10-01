package io.github.unclelem.smogbot;

import twitter4j.TwitterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class SmogBotApplication {
    public static void main(String[] args) {
        Message message = null;
        try {
            Properties properties = new Properties();
            properties.load(SmogBotApplication.class.getClassLoader().getResourceAsStream("application.properties"));
            message = new Retriever(properties).retrieve();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            TwitterPoster poster = new TwitterPoster();
            if (message != null) {
                poster.post(message);
            } else {
                poster.post(
                        new Message("Something went wrong, please call the doctor", new ArrayList<>())
                );
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}

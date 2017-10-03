package io.github.unclelem.smogbot;

import twitter4j.TwitterException;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Properties;

public class SmogBotApplication {

    private static final String ERROR_MESSAGE = "Something went wrong, please call the doctor";
    private static final String TODAY = "morning.properties";
    private static final String PROGNOSIS = "evening.properties";

    public static void main(String[] args) {
        Message message = null;
        try {
            message = new Retriever(getConfigProperties()).retrieve();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            TwitterPoster poster = new TwitterPoster();
            if (message != null) {
                poster.post(message);
            } else {
                poster.post(
                        new Message(ERROR_MESSAGE, new ArrayList<>())
                );
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private static Properties getConfigProperties() throws IOException {
        Properties properties = new Properties();
        LocalTime now = LocalTime.now();
        if (now.getHour() < 12) {
            properties.load(SmogBotApplication.class.getClassLoader().getResourceAsStream(TODAY));
        } else {
            properties.load(SmogBotApplication.class.getClassLoader().getResourceAsStream(PROGNOSIS));
        }
        return properties;
    }
}

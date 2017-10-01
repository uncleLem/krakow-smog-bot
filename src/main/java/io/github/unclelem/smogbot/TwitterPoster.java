package io.github.unclelem.smogbot;

import twitter4j.*;

import java.io.File;
import java.util.List;

/**
 * twitter4j.properties should be added in order to connect
 */
public class TwitterPoster {
    private final Twitter twitter;

    public TwitterPoster() {
        twitter = new TwitterFactory().getInstance();
    }

    public Status post(Message message) throws TwitterException {
        StatusUpdate update = new StatusUpdate(message.getText());

        List<File> mediaFiles = message.getMedia();
        if (mediaFiles != null && mediaFiles.size() > 0) {
            long[] uploaded = new long[mediaFiles.size()];
            for (int i = 0; i < mediaFiles.size(); i++) {
                uploaded[i] = twitter.uploadMedia(mediaFiles.get(i)).getMediaId();
            }

            update.setMediaIds(uploaded);
        }

        return twitter.updateStatus(update);
    }
}

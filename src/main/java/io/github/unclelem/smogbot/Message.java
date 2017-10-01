package io.github.unclelem.smogbot;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Message {
    private final String text;
    private final List<File> media;

    public Message(String text, List<File> media) {
        this.text = text;
        this.media = media;
    }

    public Message(String text, File... media) {
        this.text = text;
        this.media = Arrays.asList(media);
    }

    public String getText() {
        return text;
    }

    public List<File> getMedia() {
        return media;
    }
}

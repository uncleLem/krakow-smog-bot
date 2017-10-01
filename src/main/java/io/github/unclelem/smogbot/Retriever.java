package io.github.unclelem.smogbot;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.github.unclelem.smogbot.PropertiesKey.*;

public class Retriever {
    private final Properties properties;

    public Retriever(Properties properties) {
        this.properties = properties;
    }

    public Message retrieve() throws IOException {
        Document doc = Jsoup
                .connect(getProperty(SOURCE_URL))
                .get();
        List<File> media = new ArrayList<>();
        String[] ids = getProperty(IMG_HOLDER_IDS)
                .split(";");
        for (String id : ids) {
            if (!StringUtil.isBlank(id)) {
                media.add(getMediaFile(doc, id));
            }
        }
        return new Message(getProperty(DEFAULT_MESSAGE), media);
    }

    private File getMediaFile(Document doc, String id) throws IOException {
        String imgUrlBase = getProperty(IMG_URL_BASE_PATH);
        Element element = doc.getElementById(id);
        String href = element.attr(getProperty(IMG_HOLDER_ATTR));
        File file = File.createTempFile(id, ".png");
        file.deleteOnExit();
        URL imgURL = new URL(imgUrlBase + (imgUrlBase.endsWith("/") ? "" : "/") + href);
        try(InputStream inputStream = imgURL.openStream();
            ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
        return file;
    }

    private String getProperty(PropertiesKey key) {
        return properties.getProperty(key.toString());
    }
}

package io.github.unclelem.smogbot;

public enum PropertiesKey {
    SOURCE_URL("sourceUrl"),
    IMG_URL_BASE_PATH("imgUrlBase"),
    IMG_HOLDER_IDS("imgHolderIds"),
    IMG_HOLDER_ATTR("imgHolderAttr"),
    DEFAULT_MESSAGE("defaultMessage");

    private final String key;

    PropertiesKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}

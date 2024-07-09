package com.rumpus.common.views.Image;

abstract public class AbstractImage {

    // public enum of type of image, cloud, local, link, etc.
    public enum ImageType {
        CLOUD,
        LOCAL,
        LINK
    }

    private String name;
    private String path;
    private java.util.Map<String, String> attributes;
    private String description;

    public AbstractImage(String name, String path, String description) {
        this.name = name;
        this.path = path;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public String getDescription() {
        return this.description;
    }

    public java.util.Map<String, String> getAttributes() {
        return this.attributes;
    }
}

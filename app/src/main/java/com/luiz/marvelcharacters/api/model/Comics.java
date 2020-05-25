package com.luiz.marvelcharacters.api.model;

public class Comics {
    private int id;
    private int digitalId;
    private String title;
    private String description;
    private String resourceURI;
    private Image thumbnail;

    public int getId() {
        return id;
    }

    public int getDigitalId() {
        return digitalId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public Image getThumbnail() {
        return thumbnail;
    }
}

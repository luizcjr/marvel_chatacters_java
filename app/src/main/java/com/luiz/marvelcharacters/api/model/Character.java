package com.luiz.marvelcharacters.api.model;

public class Character {

    private int id;
    private String name;
    private String description;
    private String resourceURI;
    private Image thumbnail;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

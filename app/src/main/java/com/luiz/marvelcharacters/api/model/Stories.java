package com.luiz.marvelcharacters.api.model;

import java.util.List;

public class Stories {
    private int available;
    private int returned ;
    private String collectionURI;
    private List<Summaries> items;

    public int getAvailable() {
        return available;
    }

    public int getReturned() {
        return returned;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public List<Summaries> getItems() {
        return items;
    }
}

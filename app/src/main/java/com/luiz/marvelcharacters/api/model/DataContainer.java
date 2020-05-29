package com.luiz.marvelcharacters.api.model;

import java.util.List;

public class DataContainer<T> {

    private int offset;
    private int limit;
    private int total;
    private int count;
    private List<T> results;

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public List<T> getResults() {
        return results;
    }
}

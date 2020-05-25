package com.luiz.marvelcharacters.api.model;

import java.util.List;

public class CharacterDataContainer {

    private int offset;
    private int limit;
    private int total;
    private int count;
    private List<Character> results;

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

    public List<Character> getResults() {
        return results;
    }
}

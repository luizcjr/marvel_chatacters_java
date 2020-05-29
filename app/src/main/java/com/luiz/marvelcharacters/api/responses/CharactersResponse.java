package com.luiz.marvelcharacters.api.responses;

import com.luiz.marvelcharacters.api.model.Character;
import com.luiz.marvelcharacters.api.model.DataContainer;

public class CharactersResponse {
    private int code ;
    private String status;
    private DataContainer<Character> data;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public DataContainer<Character> getData() {
        return data;
    }
}

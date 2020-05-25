package com.luiz.marvelcharacters.api.responses;

import com.luiz.marvelcharacters.api.model.CharacterDataContainer;

public class CharactersResponse {
    private int code ;
    private String status;
    private CharacterDataContainer data;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public CharacterDataContainer getData() {
        return data;
    }
}

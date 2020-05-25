package com.luiz.marvelcharacters.api.responses;

import com.luiz.marvelcharacters.api.model.ComicsDataContainer;

public class ComicsResponse {
    private int code;
    private String status;
    private ComicsDataContainer data;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public ComicsDataContainer getData() {
        return data;
    }
}

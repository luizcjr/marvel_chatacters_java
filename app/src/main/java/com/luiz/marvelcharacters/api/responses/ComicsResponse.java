package com.luiz.marvelcharacters.api.responses;

import com.luiz.marvelcharacters.api.model.Comics;
import com.luiz.marvelcharacters.api.model.DataContainer;

public class ComicsResponse {
    private int code;
    private String status;
    private DataContainer<Comics> data;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public DataContainer<Comics> getData() {
        return data;
    }
}

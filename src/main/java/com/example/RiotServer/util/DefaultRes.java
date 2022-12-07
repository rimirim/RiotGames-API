package com.example.RiotServer.util;

import io.micrometer.core.lang.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DefaultRes {

    private int statusCode;
    private String resMessage;
    private Object data;

    public DefaultRes(int statusCode, String resMessage, @Nullable Object data) {
        this.statusCode = statusCode;
        this.resMessage = resMessage;
        this.data = data;
    }

}

package com.example.RiotServer.util;

import lombok.Getter;

public enum StatusEnum {

    OK(200, "OK"),
    DATA_REQUEST_SUCCESS(200, "DATA_REQUEST_SUCCESS"),
    CHAMPION_DATAS_INSERT_FAILED (200, "CHAMPION_DATAS_INSERT_FAILED"),
    CHAMPION_DATAS_ARE_NULL(204, "CHAMPION_DATAS_ARE_NULL"),

    BAD_REQUEST(400,"BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FAILED (420, "")
    ;

    @Getter
    private int statusCode;
    @Getter
    private String resMessage;
    private StatusEnum(int statusCode, String resMessage) {

        this.statusCode = statusCode;
        this.resMessage = resMessage;
    }

}

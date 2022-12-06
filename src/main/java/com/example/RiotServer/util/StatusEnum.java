package com.example.RiotServer.util;

public enum StatusEnum {

    OK(200, "OK"),
    BAD_REQUEST(400,"BAD_REQUEST"),
    DATA_REQUEST_SUCCESS(200, "DATA_REQUEST_SUCCESS"),
    CHAMPION_DATAS_INSERT_FAILED (200, "CHAMPION_DATAS_INSERT_FAILED"),
    CHAMPION_DATAS_ARE_NULL(204, "CHAMPION_DATAS_ARE_NULL")
    ;

    int statusCode;
    String resMessage;
    StatusEnum(int statusCode, String resMessage) {

        this.statusCode = statusCode;
        this.resMessage = resMessage;
    }

}

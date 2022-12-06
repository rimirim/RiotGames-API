package com.example.RiotServer.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DefaultRes {

    private StatusEnum statusEnum;
    private String resMessage = statusEnum.resMessage;
    private Object data;

    public DefaultRes(StatusEnum statusEnum, Object data) {
        this.statusEnum = statusEnum;
        this.data = data;
    }

}

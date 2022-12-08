package com.example.RiotServer.entity;

import lombok.Data;

@Data
public class Participants {

    private boolean win;
    private String teamPosition;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer championId;
    private Integer championLevel;
    private Integer goldEarned;
    private Integer goldSpent;
    private Integer item0;
    private Integer item1;
    private Integer item2;
    private Integer item3;
    private Integer item4;
    private Integer item5;
    private Integer item6;

}

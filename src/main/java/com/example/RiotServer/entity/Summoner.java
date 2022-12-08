package com.example.RiotServer.entity;

import lombok.Data;

import java.util.List;

@Data
public class Summoner {

    private String id;
    private String accountId;
    private String puuid;
    private String name;
    private String summonerLevel;
    private List<MatchInfo> matchInfoList;

}

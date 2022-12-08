package com.example.RiotServer.entity;

import lombok.Data;

@Data
public class MatchInfo {

    private long gameStartTimestamp;
    private long gameEndTimestamp;
    private long gameDuration;

    private long gameId;
    private String matchId;
    private Participants participants;

}

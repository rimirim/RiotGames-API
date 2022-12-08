package com.example.RiotServer.service;

import org.springframework.http.ResponseEntity;

public interface SummonerService {

    public ResponseEntity getSummonerInfo (String summonerName);
}

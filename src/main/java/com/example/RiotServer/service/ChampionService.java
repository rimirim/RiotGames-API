package com.example.RiotServer.service;

import com.example.RiotServer.entity.Champion;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ChampionService {
    public ResponseEntity saveChampion(List<Champion> championList);
    public ResponseEntity getChampion();

}

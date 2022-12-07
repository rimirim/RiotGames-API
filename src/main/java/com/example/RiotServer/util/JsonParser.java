package com.example.RiotServer.util;

import com.example.RiotServer.entity.Champion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JsonParser {
    public List<Champion> championParser (String filaName) throws JsonProcessingException {

        String jsonToString = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(filaName))
                )
        ).lines().collect(Collectors.joining());

        List<Champion> championList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        // String을 LinkedHashMap의 class로 저장
        // LinkedHaspMap은 key의 순서가 보장됨 (put을 통해 입력된 순서대로)
        Map<String, Object> jsonMap = objectMapper.readValue(jsonToString, LinkedHashMap.class);
        log.info("jsonMap" + jsonMap);
        Map<String, Object> championMap = (Map<String, Object>) jsonMap.get("data");
        log.info("champion" + championMap);
        // champion의 key값을 모두 가져옴 (여기서 key 값은 챔피언 이름으로 된 것,,)
        List<String> champNameKey = new ArrayList<String>(championMap.keySet());
        log.info("champNameKey" + champNameKey);

        for(int i = 0 ; i < champNameKey.size(); i++) {
            Map<String, Object> champInfo = (Map<String, Object>) championMap.get(champNameKey.get(i));
            Champion champion = new Champion();
            champion.setChampion_key(Integer.parseInt((String) champInfo.get("key")));
            champion.setName((String) champInfo.get("name"));
            log.info("Champion Info" + champion);

            championList.add(champion);
        }

        return championList;
    }
}

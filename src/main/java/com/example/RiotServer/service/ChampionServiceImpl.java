package com.example.RiotServer.service;

import com.example.RiotServer.entity.Champion;
import com.example.RiotServer.mapper.ChampionMapper;
import com.example.RiotServer.util.DefaultRes;
import com.example.RiotServer.util.StatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ChampionServiceImpl implements ChampionService {

    @Autowired
    private ChampionMapper championMapper;

    @Override
    public List<Champion> parseChampion(String jsonToString) throws JsonProcessingException {

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
    @Override
    public ResponseEntity saveChampion(List<Champion> championList) {

        DefaultRes res = new DefaultRes();

        try {
            if (championMapper.insertChampions(championList) > 0) {
                res.setStatusEnum(StatusEnum.OK);
                res.setData(championList);
                return new ResponseEntity(res, HttpStatus.OK);
            } else {
                res.setStatusEnum(StatusEnum.CHAMPION_DATAS_INSERT_FAILED);
                res.setData(championList);
                return new ResponseEntity(res, HttpStatus.OK);
            }
        }catch (Exception e) {
            log.info("exception" + e);
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
}

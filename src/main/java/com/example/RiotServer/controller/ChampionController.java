package com.example.RiotServer.controller;

import com.example.RiotServer.entity.Champion;
import com.example.RiotServer.service.ChampionService;
import com.example.RiotServer.util.DefaultRes;
import com.example.RiotServer.util.JsonParser;
import com.example.RiotServer.util.StatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Controller와 RestController의 차이
@RestController
@Slf4j
public class ChampionController {

    @Autowired
    private ChampionService championService;

    @ResponseBody
    @PostMapping("/champion")
    // HttpServletRequest  : 요청을 받을 때 받은 정보를 저장
    // -> RequstBody 와의 차이 ?
    // ResponseEntity = > 상태코드, 헤더, 응답데이터를 담아 클라이언트에게 응답하는 정보
    public ResponseEntity saveChampionList(HttpServletRequest request) throws JsonProcessingException {

        String authKey = request.getHeader("Authorization");

        if (authKey == "aryoo@clvs.co.kr") {

            log.info("======= Parse Champion.json =======");

            JsonParser jsonParser = new JsonParser();
            List<Champion> championList = jsonParser.championParser("lol_championList.json");

            log.info("======= Database save ChampionList =======");

            return championService.saveChampion(championList);
        } else {

            log.info("Not correct authKey ! : " + authKey);
            StatusEnum statusEnum = StatusEnum.UNAUTHORIZED;
            return new ResponseEntity(new DefaultRes(statusEnum.getStatusCode(),statusEnum.getResMessage(),null), HttpStatus.UNAUTHORIZED);

        }
    }

    @ResponseBody
    @PostMapping("/championList")
    public ResponseEntity getChampionList(HttpServletRequest request) {
        log.info("======= Get ChampionList From Database =======");

        return championService.getChampion();
    }
}

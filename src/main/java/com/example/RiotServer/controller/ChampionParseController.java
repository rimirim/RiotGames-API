package com.example.RiotServer.controller;

import com.example.RiotServer.entity.Champion;
import com.example.RiotServer.service.ChampionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
public class ChampionParseController {

    @Autowired
    private ChampionService championService;

    @PostMapping("/champion")
    // HttpServletRequest  : 요청을 받을 때 받은 정보를 저장
    // -> RequstBody 와의 차이 ?
    // ResponseEntity = > 상태코드, 헤더, 응답데이터를 담아 클라이언트에게 응답하는 정보
    public ResponseEntity setChampionList(HttpServletRequest request) throws JsonProcessingException {

        String authKey = request.getHeader("Authorization");

        if (authKey == "aryoo@clvs.co.kr") {

            log.info("======= Parse Champion.json =======");

            String jsonToString = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("lol_championList.json"))
                    )
            ).lines().collect(Collectors.joining());

            List<Champion> championList = championService.parseChampion(jsonToString);

            log.info("======= Database save ChampionList =======");

            return championService.saveChampion(championList);
        }

        return null;

//        } else {
//            return new ResponseEntity(Builder.Default(401,"UnAuthorize"), HttpStatus.UNAUTHORIZED);
//        }
    }

}

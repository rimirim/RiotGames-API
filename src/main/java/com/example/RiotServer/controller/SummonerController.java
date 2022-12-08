package com.example.RiotServer.controller;

import com.example.RiotServer.entity.NameData;
import com.example.RiotServer.service.SummonerService;
import com.example.RiotServer.util.DefaultRes;
import com.example.RiotServer.util.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class SummonerController {

    @Autowired
    private SummonerService summonerService;

    @ResponseBody
    @PostMapping("/summoner")
    public ResponseEntity getSummonerInfo (HttpServletRequest request, @RequestBody NameData nameData) {

        String authKey = request.getHeader("Authorization");
        String summonerName = nameData.getName();

        if (authKey == "aryoo@clvs.co.kr") {

            log.info("======= get summoner Info =======");
            return summonerService.getSummonerInfo(summonerName);

        } else {

            log.info("Not correct authKey ! : " + authKey);
            StatusEnum statusEnum = StatusEnum.UNAUTHORIZED;
            return new ResponseEntity(new DefaultRes(statusEnum.getStatusCode(),statusEnum.getResMessage(),null), HttpStatus.UNAUTHORIZED);

        }

    }
}

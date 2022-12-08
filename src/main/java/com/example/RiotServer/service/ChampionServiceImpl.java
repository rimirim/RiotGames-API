package com.example.RiotServer.service;

import com.example.RiotServer.entity.Champion;
import com.example.RiotServer.mapper.ChampionMapper;
import com.example.RiotServer.util.DefaultRes;
import com.example.RiotServer.util.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChampionServiceImpl implements ChampionService {

    @Autowired
    private ChampionMapper championMapper;

    @Override
    public ResponseEntity saveChampion(List<Champion> championList) {

        DefaultRes res = new DefaultRes();

        try {
            if (championMapper.insertChampions(championList) > 0) {
                StatusEnum statusEnum = StatusEnum.DATA_REQUEST_SUCCESS;
                res.setStatusCode(statusEnum.getStatusCode());
                res.setResMessage(statusEnum.getResMessage());
                res.setData(championList);
                return new ResponseEntity(res, HttpStatus.OK);
            } else {
                StatusEnum statusEnum = StatusEnum.CHAMPION_DATAS_INSERT_FAILED;
                res.setStatusCode(statusEnum.getStatusCode());
                res.setResMessage(statusEnum.getResMessage());
                res.setData(championList);
                return new ResponseEntity(res, HttpStatus.OK);
            }
        }catch (Exception e) {
            log.info("exception" + e);
            StatusEnum statusEnum = StatusEnum.FAILED;
            res.setStatusCode(statusEnum.getStatusCode());
            res.setResMessage(e.getMessage());
            res.setData(championList);
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity getChampion(){

        List<Champion> championList = championMapper.getChampionList();
        DefaultRes res = new DefaultRes();

        try {
            if(championList.size() > 0) {
                log.info("Champion List in DB " + championList);
                StatusEnum statusEnum = StatusEnum.OK;
                res.setStatusCode(statusEnum.getStatusCode());
                res.setResMessage(statusEnum.getResMessage());
                res.setData(championList);
                return new ResponseEntity(res,HttpStatus.OK);
            } else {
                log.info("Champion List not in DB " + championList);
                StatusEnum statusEnum = StatusEnum.CHAMPION_DATAS_ARE_NULL;
                res.setStatusCode(statusEnum.getStatusCode());
                res.setResMessage(statusEnum.getResMessage());
                res.setData(championList);
                return new ResponseEntity(res,HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.info("exception" + e);
            StatusEnum statusEnum = StatusEnum.FAILED;
            res.setStatusCode(statusEnum.getStatusCode());
            res.setResMessage(statusEnum.getResMessage());
            res.setData(null);
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }

    }
}

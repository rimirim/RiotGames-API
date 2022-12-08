package com.example.RiotServer.service;

import com.example.RiotServer.entity.MatchInfo;
import com.example.RiotServer.entity.Participants;
import com.example.RiotServer.entity.Summoner;
import com.example.RiotServer.util.DefaultRes;
import com.example.RiotServer.util.RiotApiCall;
import com.example.RiotServer.util.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SummonerServiceImpl implements SummonerService{

    private final String apikey = "RGAPI-7b453bc0-6b32-40c1-9fe8-f44dd2bfc72b";

    @Override
    public ResponseEntity getSummonerInfo(String summonerName) {

        DefaultRes res = new DefaultRes();

        try {

            Summoner summoner = new Summoner();
            List<MatchInfo> matchInfoList = new ArrayList<>();
            RiotApiCall apiCall = new RiotApiCall();

            log.info("======= Call Summoner =======");
            HashMap<String,Object> summonerJson = apiCall.summonerCall(summonerName);

            Map<String, Object> getBody = (Map<String, Object>) summonerJson.get("body");
            summoner.setId(String.valueOf(getBody.get("id")));
            summoner.setAccountId(String.valueOf(getBody.get("accountId")));
            summoner.setPuuid(String.valueOf(getBody.get("puuid")));
            summoner.setName(String.valueOf(getBody.get("name")));
            summoner.setSummonerLevel(String.valueOf(getBody.get("summonerLevel")));

            log.info("======= Call Match =======");
            List<Map<String, Object>> matchJson = apiCall.matchCall(summoner.getPuuid());

            for (int i = 0 ; i < matchJson.size() ; i++) {
                Map<String, Object> mBody = (Map<String, Object>) matchJson.get(i).get("body");
                Map<String, Object> mInfo = (Map<String, Object>) mBody.get("info");

                List<Object> personList = (List<Object>) mInfo.get("participants");
                Participants participants = new Participants();

                for (int j = 0 ; j < personList.size() ; j++) {

                    Map<String, Object> mParticipant = (Map<String, Object>) personList.get(j);

                    if(String.valueOf(mParticipant.get("puuid")).equals(summoner.getPuuid())){

                        participants.setWin((Boolean) mParticipant.get("win"));
                        participants.setTeamPosition(String.valueOf(mParticipant.get("teamPosition")));
                        participants.setKills((Integer) mParticipant.get("kills"));
                        participants.setDeaths((Integer) mParticipant.get("deaths"));
                        participants.setAssists((Integer) mParticipant.get("assists"));
                        participants.setChampionId((Integer) mParticipant.get("championId"));
                        participants.setChampionLevel((Integer) mParticipant.get("champLevel"));
                        participants.setGoldEarned((Integer) mParticipant.get("goldEarned"));
                        participants.setGoldSpent((Integer) mParticipant.get("goldSpent"));
                        participants.setItem0((Integer) mParticipant.get("item0"));
                        participants.setItem1((Integer) mParticipant.get("item1"));
                        participants.setItem2((Integer) mParticipant.get("item2"));
                        participants.setItem3((Integer) mParticipant.get("item3"));
                        participants.setItem4((Integer) mParticipant.get("item4"));
                        participants.setItem5((Integer) mParticipant.get("item5"));
                        participants.setItem6((Integer) mParticipant.get("item6"));
                    }
                }

                MatchInfo matchInfo = new MatchInfo();

                matchInfo.setGameStartTimestamp(Long.parseLong(String.valueOf(mInfo.get("gameStartTimestamp"))));
                matchInfo.setGameEndTimestamp(Long.parseLong(String.valueOf(mInfo.get("gameEndTimestamp"))));
                matchInfo.setGameDuration(Long.parseLong(String.valueOf(mInfo.get("gameDuration"))));
                matchInfo.setGameId(Long.parseLong(String.valueOf(mInfo.get("gameId"))));
                matchInfo.setMatchId(String.valueOf("KR_" + mInfo.get("gameId")));
                matchInfo.setParticipants(participants);

                matchInfoList.add(matchInfo);

            }

            summoner.setMatchInfoList(matchInfoList);

            StatusEnum statusEnum = StatusEnum.DATA_REQUEST_SUCCESS;
            res.setStatusCode(statusEnum.getStatusCode());
            res.setResMessage(statusEnum.getResMessage());
            res.setData(summoner);

            return new ResponseEntity(res, HttpStatus.OK);

        } catch (Exception e) {

            log.info("Exception : " + e);
            StatusEnum statusEnum = StatusEnum.BAD_REQUEST;
            res.setStatusCode(statusEnum.getStatusCode());
            res.setResMessage(e.getMessage());
            res.setData(null);
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);

        }
    }

}

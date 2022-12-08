package com.example.RiotServer.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RiotApiCall {

    private final String apikey = "RGAPI-7b453bc0-6b32-40c1-9fe8-f44dd2bfc72b";
    private HashMap<String, Object> result = new HashMap<>();

    public HashMap<String,Object> summonerCall (String summonerName) {

        try {
            String url = "https://kr.api.riotgames.com/tft/summoner/v1/summoners/by-name/";
            // RestTemplate의 문제점 : 매 Http 요청마다 Connection을 생성 (즉, 매 연결마다 Three-way HandShake를 거친다는 의미)
            // HttpComponentsClientHttpRequestFactory
            // 요청한 서버에서 응답을 늦게 주거나 할 경우 서버의 Thread 급격히 증가하는 문제
            // -> 서버의 전체적인 장애로 연결. 이러한 문제를 차단하기 위해 사용
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);

            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);
            String endPoint = url + summonerName;

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .path(endPoint)
                    .queryParam("api_key", apikey)
                    .build();
            log.info("Uri : " + uriComponents);

            ResponseEntity<Map> resultMap = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, Map.class);
            log.info("result Map : " + resultMap);

            result.put("statusCode", resultMap.getStatusCodeValue());
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody());
            log.info("result : " + result);

            return result;
        } catch (Exception e) {
            log.info("exception : " + e);
            return result;
        }

    }

    public List<Map<String, Object>> matchCall (String puuid) {

        List<Map<String, Object>> matchMapList = new ArrayList<>();

        try {
            String url3 = "https://asia.api.riotgames.com/lol/match/v5/matches/";

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);

            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            List<String> matchIdList = matchIdCall(puuid);

            for(int i = 0; i < matchIdList.size(); i++) {

                UriComponents uriComponents3 = UriComponentsBuilder
                        .fromUriString(url3 + "{matchId}")
                        .queryParam("api_key",apikey)
                        .buildAndExpand(matchIdList.get(i));

                ResponseEntity<Map> resultMap = restTemplate.exchange(uriComponents3.toString(), HttpMethod.GET, entity, Map.class);
                result.put("statusCode", resultMap.getStatusCodeValue());
                result.put("header", resultMap.getHeaders());
                result.put("body", resultMap.getBody());
                log.info("result : " + result);

                matchMapList.add(result);
            }

            return matchMapList;

        } catch (Exception e) {
            log.info("Exception : " + e);
            return matchMapList;
        }
    }

    public List<String> matchIdCall (String puuid) {

        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/";

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(factory);

        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(url+"{puuid}"+"/ids")
                .queryParam("api_key",apikey)
                .queryParam("count", 20)
                .buildAndExpand(puuid);

        ResponseEntity<Map> resultMap = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, Map.class);
        log.info("result Map : " + resultMap);

        List<String> matchIdList = (List<String>) resultMap.getBody();

        return matchIdList;

    }
}

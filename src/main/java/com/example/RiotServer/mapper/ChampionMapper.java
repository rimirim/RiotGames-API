package com.example.RiotServer.mapper;

import com.example.RiotServer.entity.Champion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChampionMapper {
    public int insertChampions (List<Champion> insert);
}

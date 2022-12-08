package com.example.RiotServer.mapper;

import com.example.RiotServer.entity.Champion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChampionMapper {
    public int insertChampions (List<Champion> insert);
    public List<Champion> getChampionList();
}

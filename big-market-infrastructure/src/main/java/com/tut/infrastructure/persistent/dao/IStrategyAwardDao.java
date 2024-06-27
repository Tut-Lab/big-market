package com.tut.infrastructure.persistent.dao;

import com.tut.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IStrategyAwardDao {

    List<StrategyAward> queryStrategyAwardList();

    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);


    String queryStrategyAwardRuleModels(Long strategyId, Integer awardId);

    Integer queryStrategyAwardCountSurplus(Long strategyId, Integer awardId);

    void updateStrategyAwardStock(StrategyAward strategyAward);
}

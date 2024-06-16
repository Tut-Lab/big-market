package com.tut.infrastructure.persistent.dao;

import com.tut.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IStrategyRuleDao {
    StrategyRule queryStrategyRule(StrategyRule strategyRule);
}

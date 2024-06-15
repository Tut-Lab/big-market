package com.tut.domain.strategy.repository;

import com.tut.domain.strategy.model.StrategyAwardEntity;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zsj 【352326430@qq.com】
 * @description 策略服务仓储接口
 * @create 2024/6/15 21:24
 */

public interface IStrategyRepository {

    List<StrategyAwardEntity>  queryAwardsByStrategyId(Long strategyId);

    void storeStrategyAwardSearchRateTable(Long strategyId, int size, LinkedHashMap<Integer, Integer> shuffledStrategyAwardSearchRateTable);

    int getRateRange(Long strategyId);

    Integer getStrategyAwardAssemble(Long strategyId, int rateRage, int i);
}

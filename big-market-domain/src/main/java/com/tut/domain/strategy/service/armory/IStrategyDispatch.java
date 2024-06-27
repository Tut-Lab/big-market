package com.tut.domain.strategy.service.armory;

/**
 * @author zsj 【352326430@qq.com】
 * @description
 * @create 2024/6/16 11:20
 */

public interface IStrategyDispatch {
    /**
     * 获得奖品ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId,String key);

    Boolean subtractionAwardStock(Long strategyId,Integer awardId);
}

package com.tut.domain.strategy.service.armory;

/**
 * @author zsj 【352326430@qq.com】
 * @description 策略装配接口
 * @create 2024/6/15 21:11
 */

public interface IStrategyArmory {
    /**
     * 策略装配
     * @param strategyId 策略ID
     * @return boolean
     */
    boolean assemblyStrategy(Long strategyId);

}

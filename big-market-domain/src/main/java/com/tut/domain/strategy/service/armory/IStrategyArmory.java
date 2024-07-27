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
    boolean assembleLotteryStrategy(Long strategyId);
    /**
     * 装配抽奖策略配置「触发的时机可以为活动审核通过后进行调用」
     *
     * @param activityId 活动ID
     * @return 装配结果
     */
    boolean assembleLotteryStrategyByActivityId(Long activityId);
}

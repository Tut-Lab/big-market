package com.tut.domain.strategy.service;

import com.tut.domain.strategy.model.entity.StrategyAwardEntity;
import com.tut.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zsj 【352326430@qq.com】
 * @description 策略奖品接口
 * @create 2024/6/28
 */
public interface IRaffleAward {
    /**
     * 根据策略ID查询抽奖奖品列表配置
     *
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);
    List<StrategyAwardEntity> queryRaffleStrategyAwardListByActivityId(Long activityId);

}

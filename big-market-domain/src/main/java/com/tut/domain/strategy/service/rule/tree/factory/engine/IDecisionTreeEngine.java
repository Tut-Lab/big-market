package com.tut.domain.strategy.service.rule.tree.factory.engine;

import com.tut.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import java.util.Date;

/**
 * @author zsj 【352326430@qq.com】
 * @description 规则树组合接口
 * @create 2024/6/25
 */
public interface IDecisionTreeEngine {
    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId, Date endTime);


}

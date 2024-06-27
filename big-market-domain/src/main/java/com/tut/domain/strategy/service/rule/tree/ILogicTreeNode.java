package com.tut.domain.strategy.service.rule.tree;

import com.tut.domain.strategy.model.entity.RuleActionEntity;
import com.tut.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author zsj 【352326430@qq.com】
 * @description 规则树接口
 * @create 2024/6/25
 */
public interface ILogicTreeNode {

    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId,String ruleValue);
}

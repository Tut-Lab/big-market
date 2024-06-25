package com.tut.domain.strategy.service.rule.tree.impl;

import com.tut.domain.strategy.model.entity.RuleActionEntity;
import com.tut.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tut.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.tut.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zsj 【352326430@qq.com】
 * @description
 * @create 2024/6/25
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardData(DefaultTreeFactory.StrategyAwardData.builder()
                        .awardId(101)
                        .awardRuleValue("1,100")
                        .build())
                .build();
    }
}

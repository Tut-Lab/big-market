package com.tut.domain.strategy.service.rule;

import com.tut.domain.strategy.model.entity.RuleActionEntity;
import com.tut.domain.strategy.model.entity.RuleMatterEntity;
import com.tut.domain.strategy.model.valobj.RuleLogicCheckTypeVO;

/**
 * @author zsj 【352326430@qq.com】
 * @description 抽奖规则过滤接口
 * @create 2024/6/17 22:11
 */

public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}

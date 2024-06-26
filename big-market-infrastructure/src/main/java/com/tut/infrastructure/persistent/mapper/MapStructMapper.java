package com.tut.infrastructure.persistent.mapper;

import com.tut.domain.strategy.model.entity.StrategyAwardEntity;
import com.tut.domain.strategy.model.entity.StrategyEntity;
import com.tut.domain.strategy.model.entity.StrategyRuleEntity;
import com.tut.domain.strategy.model.valobj.RuleTreeNodeLineVO;
import com.tut.domain.strategy.model.valobj.RuleTreeVO;
import com.tut.infrastructure.persistent.po.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author zsj 【352326430@qq.com】
 * @description
 * @create 2024/6/15 22:22
 */

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface MapStructMapper {

    MapStructMapper INSTANCE = Mappers.getMapper( MapStructMapper.class );


    StrategyAwardEntity StrategyAwardToStrategyAwardEntity(StrategyAward strategyAward);

    StrategyEntity strategyToStrategyENtity(Strategy strategy);

    StrategyRuleEntity strategyRuleToStrategyRuleEntity(StrategyRule strategyRule);

    RuleTreeVO RuleTreeTORuleTreeVO(RuleTree ruleTree);

    RuleTreeNodeLineVO  RuleTreeNodeLineVOTORuleTreeNodeLine(RuleTreeNodeLine ruleTreeNodeLine);
}

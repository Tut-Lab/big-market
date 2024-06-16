package com.tut.infrastructure.persistent.mapper;

import com.tut.domain.strategy.model.StrategyAwardEntity;
import com.tut.domain.strategy.model.StrategyEntity;
import com.tut.domain.strategy.model.StrategyRuleEntity;
import com.tut.infrastructure.persistent.po.Strategy;
import com.tut.infrastructure.persistent.po.StrategyAward;
import com.tut.infrastructure.persistent.po.StrategyRule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author zsj 【352326430@qq.com】
 * @description
 * @create 2024/6/15 22:22
 */

@Mapper
public interface StrategyMapper {

    StrategyMapper INSTANCE = Mappers.getMapper( StrategyMapper.class );


    StrategyAwardEntity StrategyAwardToStrategyAwardEntity(StrategyAward strategyAward);

    StrategyEntity strategyToStrategyENtity(Strategy strategy);

    StrategyRuleEntity strategyRuleToStrategyRuleEntity(StrategyRule strategyRule);
}

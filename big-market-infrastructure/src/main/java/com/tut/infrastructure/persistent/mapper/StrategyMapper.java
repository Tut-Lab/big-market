package com.tut.infrastructure.persistent.mapper;

import com.tut.domain.strategy.model.StrategyAwardEntity;
import com.tut.infrastructure.persistent.po.StrategyAward;
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
}

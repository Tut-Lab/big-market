package com.tut.infrastructure.persistent.dao;

import com.tut.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IStrategyDao  {
    Strategy  queryByStrategyId(Long strategyId);
}

package com.tut.domain.strategy.service;

import com.tut.domain.strategy.model.entity.RaffleAwardEntity;
import com.tut.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author zsj 【352326430@qq.com】
 * @description 抽奖策略 接口
 * @create 2024/6/17 21:50
 */

public interface IRaffleStrategy {
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);
}

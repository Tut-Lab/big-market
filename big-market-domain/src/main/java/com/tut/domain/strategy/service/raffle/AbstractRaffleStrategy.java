package com.tut.domain.strategy.service.raffle;

import com.tut.domain.strategy.model.entity.RaffleAwardEntity;
import com.tut.domain.strategy.model.entity.RaffleFactorEntity;
import com.tut.domain.strategy.service.IRaffleStrategy;

/**
 * @author zsj 【352326430@qq.com】
 * @description 抽奖策略抽象类
 * @create 2024/6/17 22:08
 */

public class AbstractRaffleStrategy implements IRaffleStrategy {
    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        return null;
    }
}

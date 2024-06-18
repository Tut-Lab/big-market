package com.tut.domain.strategy.service.raffle;

import com.tut.domain.strategy.model.entity.RaffleFactorEntity;
import com.tut.domain.strategy.model.entity.RuleActionEntity;
import com.tut.domain.strategy.repository.IStrategyRepository;
import com.tut.domain.strategy.service.armory.IStrategyDispatch;

public class DefaultRaffleStrategy extends AbstractRaffleStrategy {
    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        super(repository, strategyDispatch);
    }

    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        return null;
    }
}

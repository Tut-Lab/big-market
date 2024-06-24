package com.tut.domain.strategy.service;

import com.tut.domain.strategy.model.entity.RaffleAwardEntity;
import com.tut.domain.strategy.model.entity.RaffleFactorEntity;
import com.tut.domain.strategy.model.entity.RuleActionEntity;
import com.tut.domain.strategy.model.entity.StrategyEntity;
import com.tut.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tut.domain.strategy.model.valobj.StrategyRuleModelVO;
import com.tut.domain.strategy.repository.IStrategyRepository;
import com.tut.domain.strategy.service.IRaffleStrategy;
import com.tut.domain.strategy.service.armory.IStrategyDispatch;
import com.tut.domain.strategy.service.rule.chain.ILogicChain;
import com.tut.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.tut.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import com.tut.types.enums.ResponseCode;
import com.tut.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zsj 【352326430@qq.com】
 * @description 抽奖策略抽象类
 * @create 2024/6/17 22:08
 */

@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    // 策略仓储服务 -> domain层像一个大厨，仓储层提供米面粮油
    protected IStrategyRepository repository;
    protected IStrategyDispatch strategyDispatch;
    protected  DefaultChainFactory defaultChainFactory;

    public AbstractRaffleStrategy(IStrategyRepository repository,IStrategyDispatch strategyDispatch,DefaultChainFactory defaultChainFactory){
        this.repository = repository;
        this.strategyDispatch=strategyDispatch;
        this.defaultChainFactory = defaultChainFactory;
    }
    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1.参数校验
        Long strategyId = raffleFactorEntity.getStrategyId();
        String userId = raffleFactorEntity.getUserId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),
                    ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 责任链调用
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        Integer awardId = logicChain.logic(userId, strategyId);

        // 3. 查询奖品规则【抽奖中（拿到奖品ID时，过滤规则）、抽奖后（扣减完库存后过滤，抽奖中拦截和无库存则走兜底）】
        StrategyRuleModelVO strategyRuleModelVO = repository.queryStrategyAwardRuleModel(strategyId,awardId);

        // 4. 抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> raffleCenterEntityRuleActionEntity = this.doCheckRaffleCenterLogic(RaffleFactorEntity.builder()
                        .userId(userId)
                        .strategyId(strategyId)
                        .awardId(awardId.longValue())
                        .build(), strategyRuleModelVO.raffleCenterRuleModelList()
        );
        if(RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(raffleCenterEntityRuleActionEntity.getCode())){
            log.info("[临时日志]中奖中规则拦截，通过抽奖后规则rule_luck_award走兜底。");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中规则拦截，通过抽奖后规则rule_luck_award走兜底。")
                    .build();
        }

        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();
    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}

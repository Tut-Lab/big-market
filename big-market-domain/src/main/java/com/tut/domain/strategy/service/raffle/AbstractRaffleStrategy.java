package com.tut.domain.strategy.service.raffle;

import com.tut.domain.strategy.model.entity.RaffleAwardEntity;
import com.tut.domain.strategy.model.entity.RaffleFactorEntity;
import com.tut.domain.strategy.model.entity.RuleActionEntity;
import com.tut.domain.strategy.model.entity.StrategyEntity;
import com.tut.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import com.tut.domain.strategy.repository.IStrategyRepository;
import com.tut.domain.strategy.service.IRaffleStrategy;
import com.tut.domain.strategy.service.armory.IStrategyDispatch;
import com.tut.domain.strategy.service.rule.factory.DefaultLogicFactory;
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

    public AbstractRaffleStrategy(IStrategyRepository repository,IStrategyDispatch strategyDispatch){
        this.repository = repository;
        this.strategyDispatch=strategyDispatch;
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

        // 2. 策略查询
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);

        // 3.抽奖前 - 规则过滤

        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = this.doCheckRaffleBeforeLogic(RaffleFactorEntity.builder().userId(userId).strategyId(strategyId).build(), strategy.ruleModels());

        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(ruleActionEntity.getCode())){
            if(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode().equals(ruleActionEntity.getRuleModel())){
                // 黑名单返回固定的奖品ID
                return RaffleAwardEntity.builder()
                        .awardId(ruleActionEntity.getData().getAwardId())
                        .build();
            }else if(DefaultLogicFactory.LogicModel.RULE_WIGHT.getCode().equals(ruleActionEntity.getRuleModel())){
                // 权重根据返回的信息进行抽奖
                Long strategyId1 = ruleActionEntity.getData().getStrategyId();
                String ruleWeightValueKey = ruleActionEntity.getData().getRuleWeightValueKey();
                Integer randomAwardId = strategyDispatch.getRandomAwardId(strategyId1, ruleWeightValueKey);

                return  RaffleAwardEntity.builder()
                        .awardId(randomAwardId)
                        .build();
            }
        }

        // 4. 默认抽奖流程
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);

        return RaffleAwardEntity.builder()
                .awardId(awardId)
                .build();
    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}

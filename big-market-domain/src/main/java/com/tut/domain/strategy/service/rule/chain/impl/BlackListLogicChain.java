package com.tut.domain.strategy.service.rule.chain.impl;

import com.tut.domain.strategy.repository.IStrategyRepository;
import com.tut.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.tut.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.tut.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zsj 【352326430@qq.com】
 * @description 黑名单
 * @create 2024/6/24
 */

@Slf4j
@Component("rule_blacklist")
public class BlackListLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("抽奖责任链-黑名单开始 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());

        // 查询规则值配置
        /** 100:user001,user002,user003*/
        String ruleValue = repository.queryStrategyRuleValue(strategyId,ruleModel());

        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);
        String[] userBlockIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlockId : userBlockIds) {
            if(userId.equals(userBlockId)){
                log.info("抽奖责任链-黑名单接管 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleModel())
                        .build();
            }
        }
        // 过滤其他责任链
        log.info("抽奖责任链-黑名单放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId,strategyId);
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_BLACKLIST.getCode();
    }
}

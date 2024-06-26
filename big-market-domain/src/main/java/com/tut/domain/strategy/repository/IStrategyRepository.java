package com.tut.domain.strategy.repository;

import com.tut.domain.strategy.model.entity.StrategyAwardEntity;
import com.tut.domain.strategy.model.entity.StrategyEntity;
import com.tut.domain.strategy.model.entity.StrategyRuleEntity;
import com.tut.domain.strategy.model.valobj.RuleTreeVO;
import com.tut.domain.strategy.model.valobj.StrategyRuleModelVO;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zsj 【352326430@qq.com】
 * @description 策略服务仓储接口
 * @create 2024/6/15 21:24
 */

public interface IStrategyRepository {

    /**
     * 获取策略奖品列表
     * @param strategyId 策略ID
     * @return 策略奖品列表
     */
    List<StrategyAwardEntity>  queryAwardsByStrategyId(Long strategyId);

    /**
     * 获取策略实体
     * @param strategyId 策略ID
     * @return StrategyEntity
     */
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    /**
     * 存储抽奖策略范围值、概率查找表
     * @param key 策略ID
     * @param size 抽奖策略范围值
     * @param shuffledStrategyAwardSearchRateTable 乱序策略奖品表
     */
    void storeStrategyAwardSearchRateTable(String key, int size, LinkedHashMap<Integer, Integer> shuffledStrategyAwardSearchRateTable);

    /**
     * 查询抽奖策略范围值
     * @param strategyId 策略ID
     * @return 抽奖策略范围值
     */
    int getRateRange(String key);

    /**
     * 获取奖品ID
     * @param key 策略ID
     * @param rateRage 抽奖策略范围值
     * @param i 随机值 (0,rateRage)
     * @return 奖品ID
     */
    Integer getStrategyAwardAssemble(String  key, int i);

    /**
     * 查询策略规则
     * @param strategyId 策略ID
     * @param ruleModel  策略模型
     * @return 策略规则实体
     */
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, Long awardId, String ruleModel);
    String queryStrategyRuleValue(Long strategyId, String ruleModel);
    StrategyRuleModelVO queryStrategyAwardRuleModel(Long strategyId, Integer awardId);


    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);
}

package com.tut.domain.strategy.repository;

import com.tut.domain.strategy.model.entity.StrategyAwardEntity;
import com.tut.domain.strategy.model.entity.StrategyEntity;
import com.tut.domain.strategy.model.entity.StrategyRuleEntity;
import com.tut.domain.strategy.model.valobj.RuleTreeVO;
import com.tut.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import com.tut.domain.strategy.model.valobj.StrategyRuleModelVO;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

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

    /**
     * 缓存奖品库存
     * @param cacheKey key
     * @param awardCountSurplus 库存值
     */
    void cacheStrategyAwardCountSurplus(String cacheKey, Integer awardCountSurplus);

    Boolean subtractionAwardStock(String cacheKey);
    Boolean subtractionAwardStock(String cacheKey, Date endTime);

    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO);

    StrategyAwardStockKeyVO takeQueueValue();

    void updateStrategyAwardStock(Long strategyId, Integer awardId);
    /**
     * 根据策略ID+奖品ID的唯一值组合，查询奖品信息
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 奖品信息
     */
    StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId);
    /**
     * 查询策略ID
     *
     * @param activityId 活动ID
     * @return 策略ID
     */
    Long queryStrategyIdByActivityId(Long activityId);

    Integer queryTodayUserRaffleCount(String userId, Long strategyId);

    Map<String, Integer> queryAwardRuleLockCount(String... treeIds);
}

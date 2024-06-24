package com.tut.infrastructure.persistent.repository;

import com.tut.domain.strategy.model.entity.StrategyAwardEntity;
import com.tut.domain.strategy.model.entity.StrategyEntity;
import com.tut.domain.strategy.model.entity.StrategyRuleEntity;
import com.tut.domain.strategy.model.valobj.StrategyRuleModelVO;
import com.tut.domain.strategy.repository.IStrategyRepository;
import com.tut.infrastructure.persistent.dao.IStrategyAwardDao;
import com.tut.infrastructure.persistent.dao.IStrategyDao;
import com.tut.infrastructure.persistent.dao.IStrategyRuleDao;
import com.tut.infrastructure.persistent.mapper.StrategyMapper;
import com.tut.infrastructure.persistent.po.Strategy;
import com.tut.infrastructure.persistent.po.StrategyAward;
import com.tut.infrastructure.persistent.po.StrategyRule;
import com.tut.infrastructure.persistent.redis.IRedisService;
import com.tut.types.common.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zsj 【352326430@qq.com】
 * @description 策略服务仓储实现
 * @create 2024/6/15 21:24
 */

@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IRedisService redisService;

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyRuleDao strategyRuleDao;

    @Override
    public List<StrategyAwardEntity> queryAwardsByStrategyId(Long strategyId) {
        // 优先从缓存获取
        String  cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if (strategyAwardEntities != null && !strategyAwardEntities.isEmpty())  return strategyAwardEntities;
        // 从库中获取数据
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwards.size());
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyMapper.INSTANCE.StrategyAwardToStrategyAwardEntity(strategyAward);
            strategyAwardEntities.add(strategyAwardEntity);
        }
        // 存入缓存
        redisService.setValue(cacheKey, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntities = redisService.getValue(cacheKey);
        if (strategyEntities != null) return strategyEntities;

        Strategy strategy = strategyDao.queryByStrategyId(strategyId);
        strategyEntities = StrategyMapper.INSTANCE.strategyToStrategyENtity(strategy);
        redisService.setValue(cacheKey, strategyEntities);
        return strategyEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, int size, LinkedHashMap<Integer, Integer> shuffledStrategyAwardSearchRateTable) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        String  cacheKey = Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key;
        redisService.getMap(cacheKey).putAll(shuffledStrategyAwardSearchRateTable);
        // 2. 存储概率查找表
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, size);
    }

    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public Integer getStrategyAwardAssemble(String  key, int i) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key,i);
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        StrategyRule strategyRule = StrategyRule.builder()
                        .strategyId(strategyId)
                                .ruleModel(ruleModel)
                                        .build();

        strategyRule = strategyRuleDao.queryStrategyRule(strategyRule);
        return StrategyMapper.INSTANCE.strategyRuleToStrategyRuleEntity(strategyRule);
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Long awardId, String ruleModel) {
        StrategyRule strategyRule = StrategyRule.builder()
                        .strategyId(strategyId)
                                .awardId(awardId)
                                        .ruleModel(ruleModel)
                                                .build();

        strategyRule = strategyRuleDao.queryStrategyRule(strategyRule);

        return strategyRule.getRuleValue();
    }

    @Override
    public StrategyRuleModelVO queryStrategyAwardRuleModel(Long strategyId, Integer awardId) {
        String ruleModels = strategyAwardDao.queryStrategyAwardRuleModels(strategyId,awardId);
        StrategyRuleModelVO strategyRuleModelVO = StrategyRuleModelVO.builder().ruleModel(ruleModels).build();
        return strategyRuleModelVO;
    }
}

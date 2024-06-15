package com.tut.infrastructure.persistent.repository;

import com.tut.domain.strategy.model.StrategyAwardEntity;
import com.tut.domain.strategy.repository.IStrategyRepository;
import com.tut.infrastructure.persistent.dao.IStrategyAwardDao;
import com.tut.infrastructure.persistent.mapper.StrategyMapper;
import com.tut.infrastructure.persistent.po.StrategyAward;
import com.tut.infrastructure.persistent.redis.IRedisService;
import com.tut.types.common.Constants;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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
    public void storeStrategyAwardSearchRateTable(Long strategyId, int size, LinkedHashMap<Integer, Integer> shuffledStrategyAwardSearchRateTable) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        String  cacheKey = Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId;
        redisService.getMap(cacheKey).putAll(shuffledStrategyAwardSearchRateTable);
        // 2. 存储概率查找表
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId, size);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId);
    }

    @Override
    public Integer getStrategyAwardAssemble(Long strategyId, int rateRage, int i) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId,i);
    }
}

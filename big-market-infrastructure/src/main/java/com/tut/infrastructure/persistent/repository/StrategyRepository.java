package com.tut.infrastructure.persistent.repository;

import com.tut.domain.strategy.model.entity.StrategyAwardEntity;
import com.tut.domain.strategy.model.entity.StrategyEntity;
import com.tut.domain.strategy.model.entity.StrategyRuleEntity;
import com.tut.domain.strategy.model.valobj.*;
import com.tut.domain.strategy.repository.IStrategyRepository;
import com.tut.infrastructure.persistent.dao.*;
import com.tut.infrastructure.persistent.mapper.MapStructMapper;
import com.tut.infrastructure.persistent.po.*;
import com.tut.infrastructure.persistent.redis.IRedisService;
import com.tut.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zsj 【352326430@qq.com】
 * @description 策略服务仓储实现
 * @create 2024/6/15 21:24
 */

@Slf4j
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

    @Resource
    private IRuleTreeDao ruleTreeDao;

    @Resource
    private IRuleTreeNodeDao ruleTreeNodeDao;

    @Resource
    private IRuleTreeNodeLineDao ruleTreeNodeLineDao;


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
            StrategyAwardEntity strategyAwardEntity = MapStructMapper.INSTANCE.StrategyAwardToStrategyAwardEntity(strategyAward);
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
        strategyEntities = MapStructMapper.INSTANCE.strategyToStrategyENtity(strategy);
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
        return MapStructMapper.INSTANCE.strategyRuleToStrategyRuleEntity(strategyRule);
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
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        return queryStrategyRuleValue(strategyId, null, ruleModel);
    }

    @Override
    public StrategyRuleModelVO queryStrategyAwardRuleModel(Long strategyId, Integer awardId) {
        String ruleModels = strategyAwardDao.queryStrategyAwardRuleModels(strategyId,awardId);
        StrategyRuleModelVO strategyRuleModelVO = StrategyRuleModelVO.builder().ruleModel(ruleModels).build();
        return strategyRuleModelVO;
    }

    @Override
    public RuleTreeVO queryRuleTreeVOByTreeId(String treeId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.RULE_TREE_VO_KEY + treeId;
        RuleTreeVO ruleTreeVOCache = redisService.getValue(cacheKey);
        if (null != ruleTreeVOCache) return ruleTreeVOCache;

        // 从数据库获取
        RuleTree ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        List<RuleTreeNode> ruleTreeNodes = ruleTreeNodeDao.queryRuleTreeNodeListByTreeId(treeId);
        List<RuleTreeNodeLine> ruleTreeNodeLines = ruleTreeNodeLineDao.queryRuleTreeNodeLineListByTreeId(treeId);

        // 1. tree node line 转换Map结构
        Map<String, List<RuleTreeNodeLineVO>> ruleTreeNodeLineMap = new HashMap<>();
        for (RuleTreeNodeLine ruleTreeNodeLine : ruleTreeNodeLines) {
            RuleTreeNodeLineVO ruleTreeNodeLineVO = RuleTreeNodeLineVO.builder()
                    .treeId(ruleTreeNodeLine.getTreeId())
                    .ruleNodeFrom(ruleTreeNodeLine.getRuleNodeFrom())
                    .ruleNodeTo(ruleTreeNodeLine.getRuleNodeTo())
                    .ruleLimitType(RuleLimitTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitType()))
                    .ruleLimitValue(RuleLogicCheckTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitValue()))
                    .build();

            List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList = ruleTreeNodeLineMap.computeIfAbsent(ruleTreeNodeLine.getRuleNodeFrom(), k -> new ArrayList<>());
            ruleTreeNodeLineVOList.add(ruleTreeNodeLineVO);
        }

        // 2. tree node 转换为Map结构
        Map<String, RuleTreeNodeVO> treeNodeMap = new HashMap<>();
        for (RuleTreeNode ruleTreeNode : ruleTreeNodes) {
            RuleTreeNodeVO ruleTreeNodeVO = RuleTreeNodeVO.builder()
                    .treeId(ruleTreeNode.getTreeId())
                    .ruleKey(ruleTreeNode.getRuleKey())
                    .ruleDesc(ruleTreeNode.getRuleDesc())
                    .ruleValue(ruleTreeNode.getRuleValue())
                    .treeNodeLineVOList(ruleTreeNodeLineMap.get(ruleTreeNode.getRuleKey()))
                    .build();
            treeNodeMap.put(ruleTreeNode.getRuleKey(), ruleTreeNodeVO);
        }

        // 3. 构建 Rule Tree
        RuleTreeVO ruleTreeVODB = RuleTreeVO.builder()
                .treeId(ruleTree.getTreeId())
                .treeName(ruleTree.getTreeName())
                .treeDesc(ruleTree.getTreeDesc())
                .treeRootRuleNode(ruleTree.getTreeNodeRuleKey())
                .treeNodeMap(treeNodeMap)
                .build();

        redisService.setValue(cacheKey, ruleTreeVODB);
        return ruleTreeVODB;


    }

    @Override
    public void cacheStrategyAwardCountSurplus(String cacheKey, Integer awardCountSurplus) {
        if (null == redisService.getAtomicLong(cacheKey)) return;
        redisService.setAtomicLong(cacheKey, Long.valueOf(awardCountSurplus));
    }

    @Override
    public Boolean subtractionAwardStock(String cacheKey) {
        long surplus = redisService.decr(cacheKey);
        if (surplus < 0) {
            redisService.setValue(cacheKey, 0);
            return false;
        }
        String lockKey = cacheKey + Constants.UNDERLINE + surplus;
        Boolean lock = redisService.setNx(lockKey);
        if (!lock) {
            log.info("策略奖品库存加锁失败{}", lockKey);
        }
        return lock;
    }

    @Override
    public void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUEUE_KEY;
        RBlockingQueue<StrategyAwardStockKeyVO> blockingQueue = redisService.getBlockingQueue(cacheKey);
        RDelayedQueue<StrategyAwardStockKeyVO> delayedQueue = redisService.getDelayedQueue(blockingQueue);
        delayedQueue.offer(strategyAwardStockKeyVO,3, TimeUnit.SECONDS);
    }

    @Override
    public StrategyAwardStockKeyVO takeQueueValue() {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUEUE_KEY;
        RBlockingQueue<StrategyAwardStockKeyVO> destinationQueue = redisService.getBlockingQueue(cacheKey);
        return destinationQueue.poll();
    }

    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        StrategyAward strategyAward = new StrategyAward();
        strategyAward.setStrategyId(strategyId);
        strategyAward.setAwardId(Long.valueOf(awardId));
        strategyAwardDao.updateStrategyAwardStock(strategyAward);
    }
}

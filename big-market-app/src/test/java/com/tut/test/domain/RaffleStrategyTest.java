package com.tut.test.domain;

import com.alibaba.fastjson.JSON;
import com.tut.domain.strategy.model.entity.RaffleAwardEntity;
import com.tut.domain.strategy.model.entity.RaffleFactorEntity;
import com.tut.domain.strategy.service.IRaffleStrategy;
import com.tut.domain.strategy.service.armory.IStrategyArmory;
import com.tut.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import com.tut.domain.strategy.service.rule.filter.impl.RuleLockLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyTest {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private IRaffleStrategy raffleStrategy;

    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;
    @Resource
    private RuleLockLogicFilter ruleLockLogicFilter;
    @Before
    public void setUp(){
        log.info("测试结果:{}",strategyArmory.assemblyStrategy(100001L));
        log.info("测试结果:{}",strategyArmory.assemblyStrategy(100002L));
        log.info("测试结果:{}",strategyArmory.assemblyStrategy(100003L));
        ReflectionTestUtils.setField(ruleWeightLogicChain,"userScore",5000L);
        ReflectionTestUtils.setField(ruleLockLogicFilter,"user_count",10L);
    }
    @Test
    public void test_performRaffle(){
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .strategyId(100003L)
                .userId("zheng")
                .build();
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));

    }
    @Test
    public void test_performRaffle_blacklist(){
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .strategyId(100003L)
                .userId("user003") // 黑名单user001 user002 user003
                .build();
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));

    }
    @Test
    public void test_performRaffle_whitelist(){
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .strategyId(100003L)
                .userId("zheng") // 白名单zheng luo
                .build();
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));

    }

    @Test
    public void test_performRaffle_rule_lock(){
        RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                .strategyId(100003L)
                .userId("zhengshuaijie") // 黑名单user001 user002 user003
                .build();
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
        log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
        log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));

    }
}

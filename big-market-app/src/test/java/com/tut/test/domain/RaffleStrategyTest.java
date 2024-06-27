package com.tut.test.domain;

import com.alibaba.fastjson.JSON;
import com.tut.domain.strategy.model.entity.RaffleAwardEntity;
import com.tut.domain.strategy.model.entity.RaffleFactorEntity;
import com.tut.domain.strategy.service.IRaffleStrategy;
import com.tut.domain.strategy.service.armory.IStrategyArmory;
import com.tut.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

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

    @Before
    public void setUp(){
//        log.info("测试结果:{}",strategyArmory.assemblyStrategy(100001L));
//        log.info("测试结果:{}",strategyArmory.assemblyStrategy(100002L));
//        log.info("测试结果:{}",strategyArmory.assemblyStrategy(100003L));
        log.info("测试结果:{}",strategyArmory.assemblyStrategy(100006L));
        ReflectionTestUtils.setField(ruleWeightLogicChain,"userScore",5000L);

    }
    @Test
    public void test_performRaffle() throws InterruptedException {
        for (int i = 0; i <3; i++) {
            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .strategyId(100006L)
                    .userId("zheng")
                    .build();

            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);

            log.info("请求参数：{}", JSON.toJSONString(raffleFactorEntity));
            log.info("测试结果：{}", JSON.toJSONString(raffleAwardEntity));
        }

        new CountDownLatch(1).await();

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

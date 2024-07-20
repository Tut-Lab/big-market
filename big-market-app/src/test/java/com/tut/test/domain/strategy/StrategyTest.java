package com.tut.test.domain.strategy;

import com.tut.domain.strategy.service.armory.IStrategyArmory;
import com.tut.domain.strategy.service.armory.IStrategyDispatch;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zsj 【352326430@qq.com】
 * @description
 * @create 2024/6/15 23:13
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyTest {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private IStrategyDispatch strategyDispatch;

    @Test
    public void test_assemblyStrategy(){
        log.info("测试结果:{}",strategyArmory.assemblyStrategy(100001L));
    }

    @Test
    public void test_getRandomAwardId(){
        for (int i = 0; i < 100; i++) {
            Integer awardId = strategyDispatch.getRandomAwardId(100001L);
            log.info("中奖奖品：{}", awardId.toString());
        }
    }
    @Test
    public void test_getRandomAwardId_ruleWeightValue(){

        log.info("测试结果：{} - 4000 策略配置", strategyDispatch.getRandomAwardId(100001L, "4000:102,103,104,105"));
        log.info("测试结果：{} - 5000 策略配置", strategyDispatch.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果：{} - 6000 策略配置", strategyDispatch.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));
    }

}

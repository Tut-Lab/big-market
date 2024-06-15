package com.tut.test.domain;

import com.tut.domain.strategy.service.armory.IStrategyArmory;
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


    @Test
    public void test_assemblyStrategy(){
        boolean success = strategyArmory.assemblyStrategy(100001L);
        log.info("测试结果:{}",success);
    }

    @Test
    public void test_getRandomAwardId(){
        for (int i = 0; i < 100; i++) {
            Integer awardId = strategyArmory.getRandomAwardId(100001L);
            log.info("中奖奖品：{}", awardId.toString());

        }

    }

}

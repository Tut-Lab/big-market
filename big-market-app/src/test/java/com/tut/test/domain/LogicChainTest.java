package com.tut.test.domain;

import com.tut.domain.strategy.service.armory.IStrategyArmory;
import com.tut.domain.strategy.service.rule.chain.ILogicChain;
import com.tut.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.tut.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

/**
 * @author zsj 【352326430@qq.com】
 * @description 抽奖责任链测试，验证不同的规则走不同的责任链
 * @create 2024/6/24
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicChainTest {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;
    @Resource
    private DefaultChainFactory defaultChainFactory;


    @Before
    public void setUp() {
        // 策略装配 100001、100002、100003
        log.info("测试结果：{}", strategyArmory.assemblyStrategy(100001L));
        log.info("测试结果：{}", strategyArmory.assemblyStrategy(100002L));
        log.info("测试结果：{}", strategyArmory.assemblyStrategy(100003L));
    }
    @Test
    public void test_LogicChain_rule_blacklist() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("user001", 100001L);
        log.info("测试结果：{}", awardId);
    }

    @Test
    public void test_LogicChain_rule_weight() {
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain,"userScore",4900L);
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("user001", 100001L);
        log.info("测试结果：{}", awardId);
    }

    @Test
    public void test_LogicChain_rule_default() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("xiaofuge", 100001L);
        log.info("测试结果：{}", awardId);
    }

}

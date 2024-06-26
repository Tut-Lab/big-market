package com.tut.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.tut.domain.strategy.model.valobj.RuleTreeVO;
import com.tut.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zsj 【352326430@qq.com】
 * @description
 * @create 2024/6/26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRepositoryTest {
    @Resource
    private IStrategyRepository repository;

    @Test
    public void queryRuleTreeVOByTreeId(){
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId("tree_lock");
        log.info("测试结果：{}", JSON.toJSONString(ruleTreeVO));
    }
}

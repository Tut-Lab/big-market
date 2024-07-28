package com.tut.domain.strategy.service;

import java.util.Map;

/**
 * @author zsj
 * @description 抽奖规则接口
 * @date 2024/7/28 10:54
 */
public interface IRaffleRule {

    Map<String, Integer> queryAwardRuleLockCount(String... treeIds);
}

package com.tut.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zsj 【352326430@qq.com】
 * @description
 * @create 2024/6/27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardStockKeyVO {
    /**
     * 抽奖策略ID
     **/
    private Long strategyId;
    /**
     * 抽奖奖品ID - 内部流转使用
     **/
    private Integer awardId;
}

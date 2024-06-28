package com.tut.trigger.api.dto;

import lombok.Data;

/**
 * @author zsj 【352326430@qq.com】
 * @description 抽奖请求参数
 * @create 2024/6/28
 */
@Data
public class RaffleRequestDTO {

    /**
     * 抽奖策略ID
     **/
    private Long strategyId;
}

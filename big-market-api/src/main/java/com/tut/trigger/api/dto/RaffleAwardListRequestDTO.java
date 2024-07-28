package com.tut.trigger.api.dto;

import lombok.Data;

/**
 * @author zsj 【352326430@qq.com】
 * @description 抽奖奖品列表，请求对象
 * @create 2024/6/28
 */
@Data
public class RaffleAwardListRequestDTO {

    // 策略ID
    @Deprecated
    private Long strategyId;

    // 活动ID
    private Long activityId;

    // 用户ID
    private String userId;

}

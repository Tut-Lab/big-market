package com.tut.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zsj 【352326430@qq.com】
 * @description 抽奖奖品列表，应答对象
 * @create 2024/6/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleAwardListResponseDTO {

    /**
     * 抽奖奖品ID - 内部流转使用
     **/
    private Long awardId;
    /**
     * 抽奖奖品标题
     **/
    private String awardTitle;
    /**
     * 奖品副标题【抽奖1次后解锁】
     **/
    private String awardSubtitle;
    /**
     * 排序编号
     **/
    private Integer sort;
    /**
     * 抽奖n次后解锁
     */
    private Integer awardRuleLockCount;
    /**
     * 奖品是否解锁 - true 已解锁 false 未解锁
     */
    private Boolean  isAwardUnlock;
    /**
     * 等待解锁次数 - 规则的抽奖N次解锁 - 用户已经抽奖次数
     */
    private Integer waitUnlockCount;
}

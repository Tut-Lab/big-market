package com.tut.domain.activity.model.entity;

import lombok.Data;


/**
 * @author zsj
 * @description 参与抽奖活动实体对象
 * @date 2024/7/23 16:42
 */
@Data

public class PartakeRaffleActivityEntity {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

}

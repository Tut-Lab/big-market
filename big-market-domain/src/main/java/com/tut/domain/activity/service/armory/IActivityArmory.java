package com.tut.domain.activity.service.armory;

/**
 * @author zsj
 * @description 活动装配预热
 * @create 2024-07-20 23:24
 */
public interface IActivityArmory {

    boolean assembleActivitySku(Long sku);

    boolean assembleActivitySkuByActivityId(Long activityId);
}


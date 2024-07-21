package com.tut.domain.activity.model.valobj;

import lombok.*;

/**
 * @author zsj
 * @description活动sku库存 key 值对象
 * @date 2024/7/20 23:55
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivitySkuStockKeyVO {

    /** 商品sku */
    private Long sku;
    /** 活动ID */
    private Long activityId;

}


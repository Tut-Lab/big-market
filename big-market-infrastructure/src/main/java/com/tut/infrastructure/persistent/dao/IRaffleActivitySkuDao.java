package com.tut.infrastructure.persistent.dao;

import com.tut.infrastructure.persistent.po.RaffleActivityCount;
import com.tut.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zsj
 * @description
 * @date 2024/7/20 11:13
 */
@Mapper
public interface IRaffleActivitySkuDao {
    RaffleActivitySku  queryRaffleSku(Long sku);

    List<RaffleActivitySku> queryRaffleSkuListByActivityId(Long activityId);
    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);
}

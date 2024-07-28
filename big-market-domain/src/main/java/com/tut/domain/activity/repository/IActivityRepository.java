package com.tut.domain.activity.repository;

import com.tut.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.tut.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.tut.domain.activity.model.entity.*;
import com.tut.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;
import java.util.List;

public interface IActivityRepository {
    ActivitySkuEntity queryActivitySku(Long sku);
    List<ActivitySkuEntity> queryActivitySkuListByActivityId(Long activityId);
    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    void doSaveOrder(CreateQuotaOrderAggregate createQuotaOrderAggregate);

    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);
    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

    ActivitySkuStockKeyVO takeQueueValue();

    void clearQueueValue();

    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);

    UserRaffleOrderEntity queryNoUsedRaffleOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);

    ActivityAccountEntity queryActivityAccountByUserId(String userId, Long activityId);
    ActivityAccountMonthEntity queryActivityAccountMonthByUserId(String userId, Long activityId, String month);
    ActivityAccountDayEntity queryActivityAccountDayByUserId(String userId, Long activityId, String day);
    void saveCreatePartakeOrderAggregate(CreatePartakeOrderAggregate createPartakeOrderAggregate);

    Integer queryRaffleActivityAccountDayPartakeCount(Long activityId, String userId);
}

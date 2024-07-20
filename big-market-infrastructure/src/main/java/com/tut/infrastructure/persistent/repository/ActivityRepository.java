package com.tut.infrastructure.persistent.repository;

import com.tut.domain.activity.model.entity.ActivityCountEntity;
import com.tut.domain.activity.model.entity.ActivityEntity;
import com.tut.domain.activity.model.entity.ActivitySkuEntity;
import com.tut.domain.activity.repository.IActivityRepository;
import com.tut.infrastructure.persistent.dao.IRaffleActivityCountDao;
import com.tut.infrastructure.persistent.dao.IRaffleActivityDao;
import com.tut.infrastructure.persistent.dao.IRaffleActivitySkuDao;
import com.tut.infrastructure.persistent.mapper.ActivityMapStructMapper;
import com.tut.infrastructure.persistent.po.RaffleActivity;
import com.tut.infrastructure.persistent.po.RaffleActivityCount;
import com.tut.infrastructure.persistent.po.RaffleActivitySku;
import com.tut.infrastructure.persistent.redis.IRedisService;
import com.tut.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ConcreteTypeMunger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zsj
 * @description
 * @date 2024/7/20 13:07
 */
@Slf4j
@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IRedisService redisService;
    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    @Resource
    private IRaffleActivityDao raffleActivityDao;

    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;


    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryRaffleSku(sku);
        return ActivityMapStructMapper.INSTANCE.ActivitySkuEntityToRaffleActivitySku(raffleActivitySku);
    }

    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        String cacheKey = Constants.RedisKey.ACTIVITY_KEY + activityId;
        ActivityEntity activityEntity = redisService.getValue(cacheKey);
        if (activityEntity != null) return activityEntity;
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(activityId);
        activityEntity = ActivityMapStructMapper.INSTANCE.RaffleActivityToActivityEntity(raffleActivity);
        return activityEntity;
    }

    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redisService.getValue(cacheKey);
        if(activityCountEntity!=null) return activityCountEntity;
        RaffleActivityCount raffleActivityCounts = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
        activityCountEntity = ActivityMapStructMapper.INSTANCE.RaffleActivityCountToActivityCountEntity(raffleActivityCounts);
        return activityCountEntity;

    }
}

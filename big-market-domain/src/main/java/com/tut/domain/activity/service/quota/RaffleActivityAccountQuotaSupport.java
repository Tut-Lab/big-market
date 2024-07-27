package com.tut.domain.activity.service.quota;

import com.tut.domain.activity.model.entity.ActivityCountEntity;
import com.tut.domain.activity.model.entity.ActivityEntity;
import com.tut.domain.activity.model.entity.ActivitySkuEntity;
import com.tut.domain.activity.repository.IActivityRepository;
import com.tut.domain.activity.service.quota.rule.chain.factory.DefaultActivityChainFactory;

/**
 * @author zsj
 * @description 抽奖活动的支撑类
 * @date 2024/7/20 14:55
 */
public class RaffleActivityAccountQuotaSupport {
    protected IActivityRepository activityRepository;
    protected DefaultActivityChainFactory defaultActivityChainFactory;
    public RaffleActivityAccountQuotaSupport(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        this.activityRepository = activityRepository;
        this.defaultActivityChainFactory = defaultActivityChainFactory;
    }

    public ActivitySkuEntity queryActivitySku(Long sku) {
        return activityRepository.queryActivitySku(sku);
    }

    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }

    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }

}

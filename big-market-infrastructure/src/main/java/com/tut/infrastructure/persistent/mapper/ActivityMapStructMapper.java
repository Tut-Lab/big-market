package com.tut.infrastructure.persistent.mapper;

import com.tut.domain.activity.model.entity.*;
import com.tut.infrastructure.persistent.po.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author zsj 【352326430@qq.com】
 * @description
 * @create 2024/6/15 22:22
 */

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface ActivityMapStructMapper {

    ActivityMapStructMapper INSTANCE = Mappers.getMapper( ActivityMapStructMapper.class );
    
    ActivitySkuEntity ActivitySkuEntityToRaffleActivitySku(RaffleActivitySku raffleActivitySku);

    ActivityEntity RaffleActivityToActivityEntity(RaffleActivity raffleActivity);

    ActivityCountEntity RaffleActivityCountToActivityCountEntity(RaffleActivityCount raffleActivityCounts);

    RaffleActivityOrder CreateOrderAggregateToActivityOrderEntity(ActivityOrderEntity activityOrderEntity);

    UserRaffleOrderEntity UserRaffleOrderToUserRaffleOrderEntity(UserRaffleOrder usedRaffleOrder);

    ActivityAccountEntity RaffleActivityAccountToActivityAccountEntity(RaffleActivityAccount raffleActivityAccountRes);

    ActivityAccountMonthEntity RaffleActivityAccountMonthToActivityAccountMonthEntity(RaffleActivityAccountMonth raffleActivityAccountMonthRes);

    ActivityAccountDayEntity RaffleActivityAccountDayToActivityAccountDayEntity(RaffleActivityAccountDay raffleActivityAccountDayRes);
}

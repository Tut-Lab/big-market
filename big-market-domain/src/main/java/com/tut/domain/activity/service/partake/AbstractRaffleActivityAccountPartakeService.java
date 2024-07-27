package com.tut.domain.activity.service.partake;

import com.alibaba.fastjson.JSON;
import com.tut.domain.activity.model.aggregate.CreatePartakeOrderAggregate;
import com.tut.domain.activity.model.entity.ActivityEntity;
import com.tut.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.tut.domain.activity.model.entity.UserRaffleOrderEntity;
import com.tut.domain.activity.model.valobj.ActivityStateVO;
import com.tut.domain.activity.repository.IActivityRepository;
import com.tut.domain.activity.service.IRaffleActivityAccountPartakeService;
import com.tut.types.enums.ResponseCode;
import com.tut.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author zsj
 * @description
 * @date 2024/7/23 16:35
 */
@Slf4j
public abstract class AbstractRaffleActivityAccountPartakeService implements IRaffleActivityAccountPartakeService {

    protected final IActivityRepository activityRepository;
    public AbstractRaffleActivityAccountPartakeService(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity){
        // 0. 基础信息
        String userId = partakeRaffleActivityEntity.getUserId();
        Long activityId = partakeRaffleActivityEntity.getActivityId();
        Date currentDate = new Date();

        // 1. 活动查询
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activityId);
        // 校验；活动状态
        if ( !ActivityStateVO.open.equals(activityEntity.getState()) ){
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(),ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        // 校验；活动日期「开始时间 <- 当前时间 -> 结束时间」
        if(!activityEntity.validActivityDate(new Date())){
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(),ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }

        // 2. 查询未被使用的活动参与订单记录
        UserRaffleOrderEntity userRaffleOrderEntity = activityRepository.queryNoUsedRaffleOrder(partakeRaffleActivityEntity);
        if(null!=userRaffleOrderEntity){
            log.info("创建参与活动订单 userId:{} activityId:{} userRaffleOrderEntity:{}", userId, activityId, JSON.toJSONString(userRaffleOrderEntity));
            return userRaffleOrderEntity;
        }
        // 3. 额度账户过滤&返回账户构建对象
        CreatePartakeOrderAggregate createPartakeOrderAggregate=this.doFilterAccount(userId, activityId, currentDate);
        // 4. 构建订单
        UserRaffleOrderEntity userRaffleOrder = this.buildUserRaffleOrder(userId, activityId, currentDate);
        // 5. 填充抽奖单实体对象
        createPartakeOrderAggregate.setUserRaffleOrderEntity(userRaffleOrder);

        // 6. 保存聚合对象 - 一个领域内的一个聚合是一个事务操作
        activityRepository.saveCreatePartakeOrderAggregate(createPartakeOrderAggregate);
        log.info("创建活动抽奖单完成 userId:{} activityId:{} orderId:{}", userId, activityId, userRaffleOrder.getOrderId());

        // 7. 返回订单信息
        return  userRaffleOrder;
    }

    @Override
    public UserRaffleOrderEntity createOrder(String userId, Long activityId) {
        PartakeRaffleActivityEntity partakeRaffleActivityEntity = new PartakeRaffleActivityEntity();
        partakeRaffleActivityEntity.setUserId(userId);
        partakeRaffleActivityEntity.setActivityId(activityId);
        return createOrder(partakeRaffleActivityEntity);
    }

    protected abstract UserRaffleOrderEntity buildUserRaffleOrder(String userId, Long activityId, Date currentDate);

    protected abstract CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate) ;
}

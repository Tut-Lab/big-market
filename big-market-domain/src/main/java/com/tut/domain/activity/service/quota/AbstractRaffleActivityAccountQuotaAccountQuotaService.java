package com.tut.domain.activity.service.quota;

import com.alibaba.fastjson.JSON;
import com.tut.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.tut.domain.activity.model.entity.*;
import com.tut.domain.activity.repository.IActivityRepository;
import com.tut.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.tut.domain.activity.service.quota.rule.chain.IActionChain;
import com.tut.domain.activity.service.quota.rule.chain.factory.DefaultActivityChainFactory;
import com.tut.types.enums.ResponseCode;
import com.tut.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 抽奖活动抽象类，定义标准的流程
 * @create 2024-03-16 08:42
 */
@Slf4j
public abstract class AbstractRaffleActivityAccountQuotaAccountQuotaService extends RaffleActivityAccountQuotaSupport implements IRaffleActivityAccountQuotaService {


    public AbstractRaffleActivityAccountQuotaAccountQuotaService(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        super(activityRepository,defaultActivityChainFactory);
    }

    @Override
    public ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity) {
        // 1. 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(activityShopCartEntity.getSku());
        // 2. 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 3. 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        log.info("查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

        return ActivityOrderEntity.builder().build();
    }
    @Override
    public String createOrder(SkuRechargeEntity skuRechargeEntity){
        // 1.参数校验
        Long sku = skuRechargeEntity.getSku();
        String userId = skuRechargeEntity.getUserId();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if(null==sku|| StringUtils.isBlank(userId)|| StringUtils.isBlank(outBusinessNo)){
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 查询基础信息
        // 2.1. 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = queryActivitySku(sku);
        // 2.2. 查询活动信息
        ActivityEntity activityEntity = queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 2.3. 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        log.info("查询结果：{} {} {}", JSON.toJSONString(activitySkuEntity), JSON.toJSONString(activityEntity), JSON.toJSONString(activityCountEntity));

        // 3. 活动动作规则校验 todo 后续处理规则过滤流程，暂时也不处理责任链结果
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        boolean success  = actionChain.logic(activitySkuEntity, activityEntity, activityCountEntity);

        // 4. 构建订单聚合对象
        CreateQuotaOrderAggregate createQuotaOrderAggregate = buildOrderAggregate(skuRechargeEntity,activitySkuEntity,activityEntity,activityCountEntity);

        // 5. 保存订单
        doSaveOrder(createQuotaOrderAggregate);
        
        // 6. 返回单号
        return createQuotaOrderAggregate.getActivityOrderEntity().getOrderId();

    }

    protected abstract void doSaveOrder(CreateQuotaOrderAggregate createQuotaOrderAggregate);

    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);


}

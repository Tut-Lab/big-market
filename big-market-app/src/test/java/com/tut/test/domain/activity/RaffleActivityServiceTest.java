package com.tut.test.domain.activity;

import com.alibaba.fastjson.JSON;
import com.tut.domain.activity.model.entity.ActivityOrderEntity;
import com.tut.domain.activity.model.entity.ActivityShopCartEntity;
import com.tut.domain.activity.model.entity.SkuRechargeEntity;
import com.tut.domain.activity.service.IRaffleOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author zsj
 * @description
 * @date 2024/7/20 13:25
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityServiceTest {

    @Resource
    private IRaffleOrder raffleOrder;

    @Test
    public void test_createRaffleActivityOrder(){
        ActivityShopCartEntity activityShopCartEntity = ActivityShopCartEntity.builder()
                .sku(9011L)
                .userId("zsj")
                .build();
        ActivityOrderEntity raffleActivityOrder = raffleOrder.createRaffleActivityOrder(activityShopCartEntity);
        log.info("测试结果：{}", JSON.toJSONString(raffleActivityOrder));
    }

    @Test
    public void test_createSkuRechargeOrder(){
        SkuRechargeEntity skuRechargeEntity = SkuRechargeEntity.builder()
                .userId("xiaofuge")
                .sku(9011L)
                .outBusinessNo("700091009113")
                .build();
        String orderId = raffleOrder.createSkuRechargeOrder(skuRechargeEntity);
        log.info("测试结果:{}",orderId);
    }
}

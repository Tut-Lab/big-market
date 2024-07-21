package com.tut.test.domain.activity;

import com.alibaba.fastjson.JSON;
import com.tut.domain.activity.model.entity.ActivityOrderEntity;
import com.tut.domain.activity.model.entity.ActivityShopCartEntity;
import com.tut.domain.activity.model.entity.SkuRechargeEntity;
import com.tut.domain.activity.service.IRaffleOrder;
import com.tut.domain.activity.service.armory.IActivityArmory;
import com.tut.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

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

    @Resource
    private IActivityArmory activityArmory;

    @Before
    public  void setUp(){log.info("裝配活動：{}",activityArmory.assembleActivitySku(9011L));}
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
    public void test_createSkuRechargeOrder_duplicate(){
        SkuRechargeEntity skuRechargeEntity = SkuRechargeEntity.builder()
                .userId("xiaofuge")
                .sku(9011L)
                .outBusinessNo("700091009113")
                .build();
        String orderId = raffleOrder.createSkuRechargeOrder(skuRechargeEntity);
        log.info("测试结果:{}",orderId);
    }

    @Test
    public void test_createSkuRechargeOrder() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            try {
                SkuRechargeEntity skuRechargeEntity = SkuRechargeEntity.builder()
                        .userId("xiaofuge")
                        .sku(9011L)
                        .outBusinessNo(RandomStringUtils.randomNumeric(12))
                        .build();
                String orderId = raffleOrder.createSkuRechargeOrder(skuRechargeEntity);
                log.info("測試結果：{}",orderId);
            }catch (AppException e) {
                log.warn(e.getInfo());
            }
        }
        new CountDownLatch(1).await();


    }
}

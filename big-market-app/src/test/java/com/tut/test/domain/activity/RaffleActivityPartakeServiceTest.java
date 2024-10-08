package com.tut.test.domain.activity;


import com.alibaba.fastjson.JSON;
import com.tut.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.tut.domain.activity.model.entity.UserRaffleOrderEntity;
import com.tut.domain.activity.service.IRaffleActivityAccountPartakeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 抽奖活动订单单测
 * @create 2024-03-16 11:51
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleActivityPartakeServiceTest {

    @Resource
    private IRaffleActivityAccountPartakeService raffleActivityPartakeService;

    @Test
    public void test_createOrder() {
        // 请求参数
        PartakeRaffleActivityEntity partakeRaffleActivityEntity = new PartakeRaffleActivityEntity();
        partakeRaffleActivityEntity.setUserId("xiaofuge");
        partakeRaffleActivityEntity.setActivityId(100301L);
        // 调用接口
        UserRaffleOrderEntity userRaffleOrder = raffleActivityPartakeService.createOrder(partakeRaffleActivityEntity);
        log.info("请求参数：{}", JSON.toJSONString(partakeRaffleActivityEntity));
        log.info("测试结果：{}", JSON.toJSONString(userRaffleOrder));
    }

}

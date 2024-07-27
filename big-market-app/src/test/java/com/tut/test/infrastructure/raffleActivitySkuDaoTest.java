package com.tut.test.infrastructure;


import com.alibaba.fastjson.JSON;
import com.tut.infrastructure.persistent.dao.IRaffleActivitySkuDao;
import com.tut.infrastructure.persistent.po.RaffleActivitySku;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class raffleActivitySkuDaoTest {

    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    @Test
    public void test_queryRaffleSku() {
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryRaffleSku(9011L);
        log.info("测试结果:{}", JSON.toJSONString(raffleActivitySku));
    }
}
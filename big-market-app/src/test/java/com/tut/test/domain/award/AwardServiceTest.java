package com.tut.test.domain.award;

import com.tut.domain.award.model.entity.UserAwardRecordEntity;
import com.tut.domain.award.model.valobj.AwardStateVO;
import com.tut.domain.award.service.IAwardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.annotation.REntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @author zsj
 * @description
 * @date 2024/7/27 19:55
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardServiceTest {

    @Resource
    private IAwardService awardService;

    @Test
    public void test_saveUserAwardRecord() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            UserAwardRecordEntity userAwardRecord = UserAwardRecordEntity.builder()
                    .userId("xiaofuge")
                    .activityId(100301L)
                    .strategyId(10006L)
                    .orderId(RandomStringUtils.randomNumeric(12))
                    .awardId(101)
                    .awardTitle("OpenAI 使用次数")
                    .awardTime(new Date())
                    .awardState(AwardStateVO.create)
                    .build();
            awardService.saveUserAwardRecord(userAwardRecord);
            Thread.sleep(500);

        }
        new CountDownLatch(1).await();
    }
}

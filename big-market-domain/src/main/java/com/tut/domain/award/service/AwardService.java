package com.tut.domain.award.service;

import com.tut.domain.award.event.SendAwardMessageEvent;
import com.tut.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.tut.domain.award.model.entity.TaskEntity;
import com.tut.domain.award.model.entity.UserAwardRecordEntity;
import com.tut.domain.award.model.valobj.TaskStateVO;
import com.tut.domain.award.repository.IAwardRepository;
import com.tut.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zsj
 * @description 奖品服务
 * @date 2024/7/27 15:41
 */
@Service
@Slf4j
public class AwardService implements IAwardService{
    @Resource
    private SendAwardMessageEvent sendAwardMessageEvent;
    @Resource
    private IAwardRepository awardRepository;
    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {
        // 构建消息对象
        SendAwardMessageEvent.SendAwardMessage sendAwardMessage = new SendAwardMessageEvent.SendAwardMessage();
        sendAwardMessage.setUserId(userAwardRecordEntity.getUserId());
        sendAwardMessage.setAwardId(userAwardRecordEntity.getAwardId());
        sendAwardMessage.setAwardTitle(userAwardRecordEntity.getAwardTitle());

        BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> sendAwardMessageEventMessage = sendAwardMessageEvent.buildEventMessage(sendAwardMessage);
        // 构建任务对象
        TaskEntity taskEntity = TaskEntity.builder()
                .userId(userAwardRecordEntity.getUserId())
                .topic(sendAwardMessageEvent.topic())
                .messageId(sendAwardMessageEventMessage.getId())
                .message(sendAwardMessageEventMessage)
                .state(TaskStateVO.create)
                .build();

        // 构建聚合对象
        UserAwardRecordAggregate userAwardRecordAggregate = UserAwardRecordAggregate.builder()
                .taskEntity(taskEntity)
                .userAwardRecordEntity(userAwardRecordEntity)
                .build();
        // 存储聚合对象 - 一个事务下，用户的中奖记录
        awardRepository.saveUserAwardRecord(userAwardRecordAggregate);

        log.info("中奖记录保存完成 userId:{} orderId:{}", userAwardRecordEntity.getUserId(), userAwardRecordEntity.getOrderId());

    }
}

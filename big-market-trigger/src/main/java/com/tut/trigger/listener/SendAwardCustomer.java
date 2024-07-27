package com.tut.trigger.listener;

import com.tut.domain.award.event.SendAwardMessageEvent;
import com.tut.domain.award.service.AwardService;
import com.tut.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import javax.annotation.Resource;

/**
 * @author zsj
 * @description 用户奖品记录消息消费者
 * @date 2024/7/27 17:00
 */
@Slf4j
@Component
public class SendAwardCustomer {

    @Value("${spring.rabbitmq.topic.send_award}")
    private String topic;

    @Resource
    private AwardService awardService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_award}"))
    public void listener(String message) {
        try {
            log.info("监听用户奖品发送消息 topic: {} message: {}", topic, message);
        } catch (Exception e) {
            log.error("监听用户奖品发送消息，消费失败 topic: {} message: {}", topic, message);
            throw e;
        }
    }
}

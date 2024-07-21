package com.tut.infrastructure.event;

import com.alibaba.fastjson.JSON;
import com.tut.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zsj
 * @description 消息發送
 * @date 2024/7/20 23:58
 */
@Slf4j
@Component
public class eventPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage){
        try {
            String messageJson = JSON.toJSONString(eventMessage);
            rabbitTemplate.convertAndSend(topic,messageJson);
            log.info("發送MQ消息 topic:{} message:{}",topic,messageJson);
        }catch (Exception e){
            log.info("發送MQ消息失敗 topic:{} message:{}",topic,JSON.toJSONString(eventMessage),e);
            throw e;
        }
    }
}


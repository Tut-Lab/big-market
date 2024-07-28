package com.tut.trigger.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tut.domain.activity.service.IRaffleActivityAccountSkuStockService;
import com.tut.types.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zsj
 * @description
 * @date 2024/7/21 0:35
 */
@Slf4j
@Component
public class ActivitySkuStockZeroCustomer {
    @Value("${spring.rabbitmq.topic.activity_sku_stock_zero}")
    private String topic;
    @Resource
    private IRaffleActivityAccountSkuStockService skuStock;

    @RabbitListener(queuesToDeclare = @Queue(value = "activity_sku_stock_zero"))
    public void listener(String message){
        try {
            log.info("監聽活動sku庫存消耗為0消息 topic: {} message: {}",topic,message);
            // 轉換對象
            BaseEvent.EventMessage<Long> eventMessage = JSON.parseObject(message, new TypeReference<BaseEvent.EventMessage<Long>>() {}.getType());
            Long sku = eventMessage.getData();
            // 更新庫存
            skuStock.clearActivitySkuStock(sku);
            // 清空隊列 [此時就不需要延遲更新數據庫記錄了] todo 清空时，需要设定sku标识，不能全部清空
            skuStock.clearQueueValue();
        }catch (Exception e){
            log.error("監聽活動sku庫存消耗為0消息，失敗 topic: {} message: {}",topic,message);
            throw e;
        }
    }
}

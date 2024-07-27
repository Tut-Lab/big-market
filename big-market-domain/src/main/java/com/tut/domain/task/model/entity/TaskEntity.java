package com.tut.domain.task.model.entity;

import lombok.Data;

/**
 * @author zsj
 * @description 任务实体
 * @date 2024/7/27 17:13
 */
@Data
public class TaskEntity {

    /** 活动ID */
    private String userId;
    /** 消息主题 */
    private String topic;
    /** 消息编号 */
    private String messageId;
    /** 消息主体 */
    private String message;

}
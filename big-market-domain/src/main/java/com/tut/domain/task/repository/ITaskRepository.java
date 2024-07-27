package com.tut.domain.task.repository;

import com.tut.domain.task.model.entity.TaskEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zsj
 * @description
 * @date 2024/7/27 17:17
 */

public interface ITaskRepository {
    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);
}

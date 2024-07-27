package com.tut.domain.task.service;

import com.tut.domain.task.model.entity.TaskEntity;
import com.tut.domain.task.repository.ITaskRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zsj
 * @description 消息任务服务
 * @date 2024/7/27 17:09
 */
@Service
public class TaskService implements ITaskService {

    @Resource
    private ITaskRepository taskRepository;

    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        return taskRepository.queryNoSendMessageTaskList();
    }

    @Override
    public void sendMessage(TaskEntity taskEntity) {
        taskRepository.sendMessage(taskEntity);
    }

    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        taskRepository.updateTaskSendMessageCompleted(userId, messageId);
    }

    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        taskRepository.updateTaskSendMessageFail(userId, messageId);
    }


}

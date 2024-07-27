package com.tut.infrastructure.persistent.repository;

import com.tut.domain.task.model.entity.TaskEntity;
import com.tut.domain.task.repository.ITaskRepository;
import com.tut.infrastructure.event.EventPublisher;
import com.tut.infrastructure.persistent.dao.ITaskDao;
import com.tut.infrastructure.persistent.mapper.TaskMapStructMapper;
import com.tut.infrastructure.persistent.po.Task;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zsj
 * @description
 * @date 2024/7/27 17:18
 */
@Repository
public class TaskRepository implements ITaskRepository {
    @Resource
    private ITaskDao taskDao;
    @Resource
    private EventPublisher eventPublisher;
    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        List<Task> tasks = taskDao.queryNoSendMessageTaskList();
        return TaskMapStructMapper.INSTANCE.TaskToTaskEntity(tasks);
    }

    @Override
    public void sendMessage(TaskEntity taskEntity) {
        eventPublisher.publish(taskEntity.getTopic(),taskEntity.getMessage());
    }

    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        Task task = Task.builder()
                .userId(userId)
                .messageId(messageId)
                .build();
        taskDao.updateTaskSendMessageCompleted(task);
    }

    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        Task task = Task.builder()
                .userId(userId)
                .messageId(messageId)
                .build();
        taskDao.updateTaskSendMessageFail(task);
    }
}

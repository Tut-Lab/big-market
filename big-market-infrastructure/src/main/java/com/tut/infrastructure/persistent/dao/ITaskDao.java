package com.tut.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.tut.infrastructure.persistent.po.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 任务表，发送MQ
 * @create 2024-04-03 15:57
 */
@Mapper
public interface ITaskDao {
    void insert(Task task);
    @DBRouter
    void updateTaskSendMessageCompleted(Task task);
    @DBRouter
    void updateTaskSendMessageFail(Task task);
    List<Task> queryNoSendMessageTaskList();
}

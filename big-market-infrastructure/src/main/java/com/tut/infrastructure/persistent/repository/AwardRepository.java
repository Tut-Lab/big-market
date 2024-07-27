package com.tut.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import com.alibaba.fastjson.JSON;
import com.tut.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.tut.domain.award.model.entity.TaskEntity;
import com.tut.domain.award.model.entity.UserAwardRecordEntity;
import com.tut.domain.award.repository.IAwardRepository;
import com.tut.infrastructure.event.EventPublisher;
import com.tut.infrastructure.persistent.dao.ITaskDao;
import com.tut.infrastructure.persistent.dao.IUserAwardRecordDao;
import com.tut.infrastructure.persistent.mapper.AwardMapStructMapper;
import com.tut.infrastructure.persistent.po.Task;
import com.tut.infrastructure.persistent.po.UserAwardRecord;
import com.tut.infrastructure.persistent.po.UserRaffleOrder;
import com.tut.types.enums.ResponseCode;
import com.tut.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author zsj
 * @description
 * @date 2024/7/27 16:06
 */
@Repository
@Slf4j
public class AwardRepository implements IAwardRepository {
    @Resource
    private IUserAwardRecordDao userAwardRecordDao;
    @Resource
    private ITaskDao taskDao;
    @Resource
    private IUserAwardRecordDao userRaffleOrderDao;
    @Resource
    private IDBRouterStrategy dbRouter;
    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private EventPublisher eventPublisher;

    @Override
    public void saveUserAwardRecord(UserAwardRecordAggregate aggregate) {
        UserAwardRecordEntity userAwardRecordEntity = aggregate.getUserAwardRecordEntity();
        TaskEntity taskEntity = aggregate.getTaskEntity();

        String userId = userAwardRecordEntity.getUserId();
        Long activityId = userAwardRecordEntity.getActivityId();
        Integer awardId = userAwardRecordEntity.getAwardId();

        UserAwardRecord userAwardRecord = AwardMapStructMapper.INSTANCE.UserAwardRecordEntityToUserAwardRecord(userAwardRecordEntity);

        Task task = new Task();
        task.setUserId(taskEntity.getUserId());
        task.setTopic(taskEntity.getTopic());
        task.setMessageId(taskEntity.getMessageId());
        task.setMessage(JSON.toJSONString(taskEntity.getMessage()));
        task.setState(taskEntity.getState().getCode());

        UserRaffleOrder userRaffleOrderReq = new UserRaffleOrder();
        userRaffleOrderReq.setUserId(userAwardRecordEntity.getUserId());
        userRaffleOrderReq.setOrderId(userAwardRecordEntity.getOrderId());

        try {
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {

                    // 写入记录
                    userAwardRecordDao.insert(userAwardRecord);
                    // 写入任务
                    taskDao.insert(task);
                    // 更新抽奖单
                    int count = userRaffleOrderDao.updateUserRaffleOrderStateUsed(userRaffleOrderReq);
                    if (1 != count) {
                        status.setRollbackOnly();
                        log.error("写入中奖记录，用户抽奖单已使用过，不可重复抽奖 userId: {} activityId: {} awardId: {}", userId, activityId, awardId);
                        throw new AppException(ResponseCode.ACTIVITY_ORDER_ERROR.getCode(), ResponseCode.ACTIVITY_ORDER_ERROR.getInfo());
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入中奖记录，唯一索引冲突 userId: {} activityId: {} awardId: {}", userId, activityId, awardId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), e);
                }
            });
        }finally {
            dbRouter.clear();
        }
        try {
            // 发送消息【在事务外执行，如果失败还有任务补偿】
            eventPublisher.publish(task.getTopic(), task.getMessage());
            // 更新数据库记录，task 任务表
            taskDao.updateTaskSendMessageCompleted(task);
        } catch (Exception e) {
            log.error("写入中奖记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }

    }
}

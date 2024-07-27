package com.tut.domain.award.model.aggregate;

import com.tut.domain.award.model.entity.TaskEntity;
import com.tut.domain.award.model.entity.UserAwardRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zsj
 * @description 用户中奖记录聚合对象 【聚合代表一个事务操作】
 * @date 2024/7/27 16:04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRecordAggregate {

    private UserAwardRecordEntity userAwardRecordEntity;

    private TaskEntity taskEntity;
}

package com.tut.domain.award.repository;

import com.tut.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author zsj
 * @description 奖品服务仓储接口
 * @date 2024/7/27 15:40
 */
public interface IAwardRepository {
    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);
}

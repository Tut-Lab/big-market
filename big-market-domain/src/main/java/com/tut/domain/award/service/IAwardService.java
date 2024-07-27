package com.tut.domain.award.service;

import com.tut.domain.award.model.entity.UserAwardRecordEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zsj
 * @description 奖品服务接口
 * @date 2024/7/27 15:33
 */

public interface IAwardService {
    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);
}

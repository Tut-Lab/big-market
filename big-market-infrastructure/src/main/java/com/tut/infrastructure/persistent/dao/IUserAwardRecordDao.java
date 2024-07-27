package com.tut.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.tut.infrastructure.persistent.po.UserAwardRecord;
import com.tut.infrastructure.persistent.po.UserRaffleOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 用户中奖记录表
 * @create 2024-04-03 15:57
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserAwardRecordDao {
    void insert(UserAwardRecord userAwardRecord);

    int updateUserRaffleOrderStateUsed(UserRaffleOrder userRaffleOrderReq);
}

package com.tut.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import com.tut.infrastructure.persistent.po.RaffleActivityAccount;
import com.tut.infrastructure.persistent.po.RaffleActivityAccountDay;
import com.tut.infrastructure.persistent.po.RaffleActivityAccountMonth;
import com.tut.infrastructure.persistent.po.RaffleActivityOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 抽奖活动账户表
 * @create 2024-03-09 10:05
 */
@Mapper
public interface IRaffleActivityAccountDao {
    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

    void insert(RaffleActivityAccount raffleActivityAccount);

    int updateActivityAccountSubtractionQuota(RaffleActivityAccount raffleActivityAccount);
    int updateActivityAccountMonthSubtractionQuota(RaffleActivityAccount raffleActivityAccount);
    int updateActivityAccountDaySubtractionQuota(RaffleActivityAccount raffleActivityAccount);

    void updateActivityAccountMonthSurplusImageQuota(RaffleActivityAccount build);

    void updateActivityAccountDaySurplusImageQuota(RaffleActivityAccount build);

    @DBRouter
    RaffleActivityAccount queryActivityAccountByUserId(RaffleActivityAccount raffleActivityAccountReq);
}

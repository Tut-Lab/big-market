package com.tut.trigger.api;

import com.tut.trigger.api.dto.RaffleAwardListRequestDTO;
import com.tut.trigger.api.dto.RaffleAwardListResponseDTO;
import com.tut.trigger.api.dto.RaffleStrategyRequestDTO;
import com.tut.trigger.api.dto.RaffleStrategyResponseDTO;
import com.tut.types.model.Response;

import java.util.List;

/**
 * @author zsj 【352326430@qq.com】
 * @description 抽奖服务接口
 * @create 2024/6/28
 */
public interface IRaffleStrategyService {

    /**
     * 策略装配接口
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    Response<Boolean> strategyArmory(Long strategyId);
    /**
     * 查询抽奖奖品列表配置
     *
     * @param request 抽奖奖品列表查询请求参数
     * @return 奖品列表数据
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO request);
    /**
     * 随机抽奖接口
     *
     * @param request 请求参数
     * @return 抽奖结果
     */
    Response<RaffleStrategyResponseDTO> randomRaffle(RaffleStrategyRequestDTO request);
}

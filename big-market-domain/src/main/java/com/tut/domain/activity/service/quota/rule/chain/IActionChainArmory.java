package com.tut.domain.activity.service.quota.rule.chain;


/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 责任链装配
 * @create 2024-07-20 15:05
 */
public interface IActionChainArmory {
    IActionChain next();
    IActionChain appendNext(IActionChain next);
}

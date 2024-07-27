package com.tut.domain.activity.service.quota.rule.chain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zsj
 * @description
 * @date 2024/7/20 15:15
 */
@Slf4j
public abstract class AbstractActionChain implements IActionChain{
    private IActionChain next;

    @Override
    public IActionChain next() {
        return next;
    }

    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }

}

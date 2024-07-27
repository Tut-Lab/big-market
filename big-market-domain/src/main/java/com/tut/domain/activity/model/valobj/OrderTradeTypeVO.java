package com.tut.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zsj
 * @description
 * @date 2024/7/27 12:04
 */
@Getter
@AllArgsConstructor
public enum OrderTradeTypeVO {

    credit_pay_trade("credit_pay_trade","积分兑换，需要支付类交易"),
    rebate_no_pay_trade("rebate_no_pay_trade", "返利奖品，不需要支付类交易"),
            ;

    private final String code;
    private final String desc;
}

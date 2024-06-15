package com.tut.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;


@Data
public class Strategy {

    /**
     * 自增ID
     **/
    private Long id;
    /**
     * 抽奖策略ID
     **/
    private Long strategy_id;
    /**
     * 抽奖策略描述
     **/
    private String strategy_desc;
    /**
     * 规则模型，rule配置的模型同步到此表，便于使用
     **/
    private String rule_models;
    /**
     * 创建时间
     **/
    private Date create_time;
    /**
     * 更新时间
     **/
    private Date update_time;


}

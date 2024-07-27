package com.tut.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zsj
 * @description 任务状态值对象
 * @date 2024/7/27 15:38
 */
@Getter
@AllArgsConstructor
public enum TaskStateVO {
    create("create", "创建"),
    complete("complete", "发送完成"),
    fail("fail", "发送失败"),
    ;

    private final String code;
    private final String desc;
}

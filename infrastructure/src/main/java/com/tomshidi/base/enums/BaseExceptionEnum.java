package com.tomshidi.base.enums;

import com.tomshidi.base.constant.ModuleCode;

/**
 * @author tomshidi
 * @date 2023/1/29 14:48
 */
public enum BaseExceptionEnum {
    /**
     * 异常类型枚举
     */
    COMMON_EXCEPTION(-1, "通用异常"),
    RECORD_DATA_NOT_FOUND(1001, "数据不存在"),
    PARENT_NODE_NOT_FOUND(1002, "父节点数据不存在"),
    FIELD_NOT_FOUND(1003, "反射待获取字段不存在"),
    MODEL_DIC_NOT_FOUND(ModuleCode.MODEL_CODE + 1, "模型目录数据不存在"),
    MODEL_INFO_NOT_FOUND(ModuleCode.MODEL_CODE + 2, "模型信息数据不存在"),
    MODEL_PARAM_NOT_FOUND(ModuleCode.MODEL_CODE + 3, "模型参数数据不存在"),
    MODEL_DIC_NAME_ALREADY_EXIST(ModuleCode.MODEL_CODE + 4, "目录名已存在"),
    MODEL_INFO_NAME_ALREADY_EXIST(ModuleCode.MODEL_CODE + 5, "模型名已存在"),
    ;

    private Integer code;

    private String message;

    BaseExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

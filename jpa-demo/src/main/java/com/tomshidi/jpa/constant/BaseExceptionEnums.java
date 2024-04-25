package com.tomshidi.jpa.constant;

import com.tomshidi.base.constant.ModuleCode;

/**
 * @author tomshidi
 * @since 2024/4/17 14:45
 */
public enum BaseExceptionEnums {
    /**
     * 异常信息
     */
    MODEL_DIC_NOT_FOUND(ModuleCode.MODEL_CODE + 1, "模型目录数据不存在"),
    MODEL_INFO_NOT_FOUND(ModuleCode.MODEL_CODE + 2, "模型信息数据不存在"),
    MODEL_PARAM_NOT_FOUND(ModuleCode.MODEL_CODE + 3, "模型参数数据不存在"),
    MODEL_DIC_NAME_ALREADY_EXIST(ModuleCode.MODEL_CODE + 4, "目录名已存在"),
    MODEL_INFO_NAME_ALREADY_EXIST(ModuleCode.MODEL_CODE + 5, "模型名已存在"),;

    private Integer code;

    private String message;

    BaseExceptionEnums(Integer code, String message) {
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

package com.tomshidi.demo.enums;

/**
 * @author tomshidi
 * @date 2023/1/29 14:48
 */
public enum BaseExceptionEnum {
    /**
     * 异常类型枚举
     */
    COMMON_EXCEPTION(-1, "通用异常"),
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

package com.tomshidi.base.exceptions;


import com.tomshidi.base.enums.BaseExceptionEnum;

/**
 * @author tomshidi
 * @date 2023/1/29 14:52
 */
public class BaseException extends RuntimeException {
    private Integer code;

    private String message;

    public BaseException(BaseExceptionEnum baseExceptionEnum) {
        this(baseExceptionEnum.getCode(), baseExceptionEnum.getMessage());
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

package com.tomshidi.demo.dto;

import com.tomshidi.demo.exceptions.BaseException;

/**
 * @author tomshidi
 * @date 2023/1/29 14:46
 */
public class ExceptionResponse<T> extends CommonResponse<T> {

    public ExceptionResponse(BaseException baseException) {
        this(baseException.getCode(), baseException.getMessage(), null);
    }

    public ExceptionResponse(Integer code, String message, T data) {
        super(code, message, data);
    }
}

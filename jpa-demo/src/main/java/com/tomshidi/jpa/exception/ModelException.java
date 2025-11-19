package com.tomshidi.jpa.exception;

import com.tomshidi.base.exceptions.BaseException;
import com.tomshidi.jpa.constant.BaseExceptionEnums;

/**
 * @author tomshidi
 * @since 2024/4/25 10:57
 */
public class ModelException extends BaseException {
    public ModelException(BaseExceptionEnums baseExceptionEnums) {
        super(baseExceptionEnums.getCode(), baseExceptionEnums.getMessage());
    }
}

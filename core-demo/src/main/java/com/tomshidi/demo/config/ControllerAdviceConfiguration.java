package com.tomshidi.demo.config;

import com.tomshidi.demo.dto.CommonResponse;
import com.tomshidi.demo.dto.ExceptionResponse;
import com.tomshidi.demo.enums.BaseExceptionEnum;
import com.tomshidi.demo.exceptions.BaseException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author tomshidi
 * @date 2023/1/29 14:37
 */
@Configuration
@RestControllerAdvice(basePackages = "com.tomshidi.demo")
public class ControllerAdviceConfiguration implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(Throwable.class)
    public Object globalExceptionHandler(Throwable throwable) {
        if (throwable instanceof BaseException) {
            return new ExceptionResponse<>((BaseException) throwable);
        }
        return new ExceptionResponse<>(BaseExceptionEnum.COMMON_EXCEPTION.getCode(), throwable.getMessage(), null);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (!selectedContentType.includes(MediaType.APPLICATION_JSON)) {
            return body;
        }
        CommonResponse<Object> result = CommonResponse.SUCCESS();
        result.setData(body);
        return result;
    }
}

package com.tomshidi.demo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tomshidi.base.dto.CommonResponse;
import com.tomshidi.base.dto.ExceptionResponse;
import com.tomshidi.base.enums.BaseExceptionEnum;
import com.tomshidi.base.exceptions.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tomshidi
 * @since 2023/1/29 14:37
 */
@Configuration
@RestControllerAdvice(basePackages = "com.tomshidi.demo")
public class ControllerAdviceConfiguration implements ResponseBodyAdvice<Object>, RequestBodyAdvice {

    private final static Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().setPrettyPrinting().create();
    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerAdviceConfiguration.class);

    @ExceptionHandler(Throwable.class)
    public Object globalExceptionHandler(Throwable throwable) {
        LOGGER.error("", throwable);
        if (throwable instanceof BaseException) {
            return new ExceptionResponse<>((BaseException) throwable);
        }
        return new ExceptionResponse<>(BaseExceptionEnum.COMMON_EXCEPTION.getCode(), throwable.getMessage(), null);
    }

    @InitBinder
    public void registerPersonalEditor(WebDataBinder webDataBinder) {
//        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), false));
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
        // 如果没有该判断，那么当controller中方法返回参数声明为Object且实际返回String类型时会报类型转换异常
//        if (!selectedContentType.includes(MediaType.APPLICATION_JSON)) {
//            return body;
//        }
        CommonResponse<Object> result = CommonResponse.SUCCESS();
        result.setData(body);
        // 返回值类型为CharSequence的接口，如果body变为其他类型，
        // 会导致StringHttpMessageConverter中发生入参类型不匹配报错
        if (body instanceof CharSequence) {
            return GSON.toJson(result);
        }
        return result;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Map<String, Object> logMap = new HashMap(8);
        String params = request.getQueryString();
        if (ObjectUtils.isEmpty(params)) {
            params = "( Empty Content )";
        }

        logMap.put("Method", request.getMethod());
        logMap.put("Path", request.getServletPath());
        logMap.put("Content-Length", request.getContentLength());
        logMap.put("Cookies", request.getHeader("Cookie"));
        logMap.put("Referer", request.getHeader("Referer"));
        logMap.put("Content-Type", request.getContentType());
        logMap.put("User-Agent", request.getHeader("User-Agent"));

        LOGGER.info("接收请求:\n" + GSON.toJson(logMap) + "\n参数内容:\n" + params);
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}

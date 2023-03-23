package com.tomshidi.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author tomshidi
 * @since 2023/3/22 17:30
 */
//@Aspect
//@Component
public class BaseAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAspect.class);

    @Around("execution(public * com.tomshidi.demo.controller.*.*(..))*")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        LOGGER.info(Arrays.asList(parameterNames).toString());
        return joinPoint.proceed();
    }
}

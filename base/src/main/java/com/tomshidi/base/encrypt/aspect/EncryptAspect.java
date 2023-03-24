package com.tomshidi.base.encrypt.aspect;

import com.tomshidi.base.encrypt.helper.SecurityHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author tomshidi
 * @since 2023/3/22 14:01
 */
@Aspect
@Component
public class EncryptAspect {

    @Around("execution(public * com.tomshidi..controller.*.*(..))*")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();
        SecurityHelper.encrypt(parameters, args);
        Object returnValue = joinPoint.proceed(args);
        returnValue = SecurityHelper.decrypt(method, returnValue);
        return returnValue;
    }
}

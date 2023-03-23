package com.tomshidi.base.encrypt.aspect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tomshidi.base.encrypt.annotation.Encrypt;
import com.tomshidi.base.exceptions.BaseException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author tomshidi
 * @since 2023/3/22 14:01
 */
@Aspect
@Component
public class EncryptAspect {

    @Value("${do1.addressbook.encrypt.sm4.key:nGKoZHZZZJ0Q-NZZ}")
    private String key;

    @Value("${do1.addressbook.encrypt.sm4.iv:1W320KJR4GZ5XAVQ}")
    private String iv;


    @Around("execution(public * com.tomshidi..controller.*.*(..))*")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        Object[] args = joinPoint.getArgs();
        encrypt(parameters, args);
        Object returnValue = joinPoint.proceed(args);
        return returnValue;
    }

    /**
     * 对方法特定入参进行加密处理
     *
     * @param parameters  方法入参类型列表
     * @param paramValues 方法入参值列表
     * @throws IllegalAccessException 成员变量访问权限异常
     * @throws NoSuchFieldException   找不到字段异常
     */
    public void encrypt(Parameter[] parameters, Object[] paramValues) throws IllegalAccessException, NoSuchFieldException {
        Class<?>[] parameterTypes = new Class<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameterTypes[i] = parameters[i].getType();
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            Parameter parameter = parameters[i];
            Object arg = paramValues[i];
            if (parameter.isAnnotationPresent(Encrypt.class)) {
                Encrypt encrypt = parameter.getAnnotation(Encrypt.class);
                if (parameterType.isPrimitive()) {
                    throw new BaseException("不支持加密基础类型");
                } else if (String.class.isAssignableFrom(parameterType)) {
                    paramValues[i] = encrypt.encryptType().encrypt(arg.toString(), key, iv);
                } else if (Map.class.isAssignableFrom(parameterType)) {
                    // 这种场景下，map的key要与实体类的字段名保持一致
                    Class<?> targetType = encrypt.targetType();
                    if (Void.class.isAssignableFrom(targetType)) {
                        throw new BaseException("加密参数为map时必须指定targetType");
                    }
                    entityFieldEncrypt((Map<String, Object>) arg, targetType);
                } else if (List.class.isAssignableFrom(parameterType) || Set.class.isAssignableFrom(parameterType)) {
                    String targetName = encrypt.targetName();
                    Class<?> targetType = encrypt.targetType();
                    // 集合参数需要指定实体类，否则无法判断是否需要加密
                    if (Void.class.isAssignableFrom(targetType)) {
                        throw new BaseException("加密参数为list/set时必须指定targetType");
                    }
                    // 集合参数需要指定实体类里字段，否则无法判断是否需要加密
                    if (ObjectUtils.isEmpty(targetName)) {
                        throw new BaseException("加密参数为list/set时必须指定targetName");
                    }
                    entityFieldEncrypt((Collection<Object>) arg, targetType, targetName);
                } else {
                    Class<?> targetType = encrypt.targetType();
                    // 简化注解字段填写，当直接使用实体类作为入参时，可以不指定targetType
                    if (Void.class.isAssignableFrom(targetType)) {
                        targetType = parameterType;
                    }
                    if (!targetType.isAssignableFrom(parameterType)) {
                        throw new BaseException("加密参数为自定义实体类时需要指定targetType");
                    }
                    entityFieldEncrypt(arg, targetType);
                }
            }
        }
    }

    /**
     * 对实体类对象加密
     *
     * @param o          实体类对象
     * @param targetType 实体类类型
     * @throws IllegalAccessException 成员变量访问权限异常
     */
    private void entityFieldEncrypt(Object o, Class<?> targetType) throws IllegalAccessException {
        Field[] declaredFields = targetType.getDeclaredFields();
        for (Field field : declaredFields) {
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (encrypt != null) {
                field.setAccessible(true);
                Object value = field.get(o);
                if (ObjectUtils.isEmpty(value)) {
                    continue;
                }
                String encryptStr = encrypt.encryptType().encrypt(value.toString(), key, iv);
                field.set(o, encryptStr);
                field.setAccessible(false);
            }
        }
    }

    /**
     * 对map类型参数加密
     *
     * @param map        待处理数据
     * @param targetType 对应的实体类类型
     * @throws IllegalAccessException 成员变量访问权限异常
     */
    private void entityFieldEncrypt(Map<String, Object> map, Class<?> targetType) throws IllegalAccessException {
        Gson gson = new GsonBuilder().create();
        Object o = gson.fromJson(gson.toJson(map), targetType);
        Field[] declaredFields = targetType.getDeclaredFields();
        for (Field field : declaredFields) {
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (encrypt != null) {
                field.setAccessible(true);
                Object value = field.get(o);
                field.setAccessible(false);
                if (ObjectUtils.isEmpty(value)) {
                    continue;
                }
                String encryptStr = encrypt.encryptType().encrypt(value.toString(), key, iv);
                String fieldName = field.getName();
                map.put(fieldName, encryptStr);
            }
        }
    }

    /**
     * 对集合数据加密
     *
     * @param collection 明文数据集合
     * @param targetType 对应的实体类类型
     * @param targetName 对应实体类中的字段
     * @throws NoSuchFieldException 找不到字段异常
     */
    private void entityFieldEncrypt(Collection<Object> collection, Class<?> targetType, String targetName) throws NoSuchFieldException {
        Field field = targetType.getDeclaredField(targetName);
        Encrypt encrypt = field.getAnnotation(Encrypt.class);
        String encryptStr;
        Collection<String> encryptCollection = collection instanceof List ? new ArrayList<>(collection.size()) : new HashSet<>(collection.size());
        for (Object item : collection) {
            encryptStr = encrypt.encryptType().encrypt(item.toString(), key, iv);
            encryptCollection.add(encryptStr);
        }
        // 移除原明文数据
        collection.removeAll(collection);
        // 填充密文数据
        collection.addAll(encryptCollection);
    }
}

package com.tomshidi.base.encrypt.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tomshidi.base.encrypt.annotation.Encrypt;
import com.tomshidi.base.encrypt.config.EncryptConfig;
import com.tomshidi.base.exceptions.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author tangshili
 * @since 2023/3/24 16:51
 */
@Component
public class SecurityHelper {

    private static EncryptConfig encryptConfig;

    @Autowired
    public void setEncryptConfig(EncryptConfig encryptConfig) {
        SecurityHelper.encryptConfig = encryptConfig;
    }

    /**
     * 返回参数解密
     *
     * @param method      被拦截的方法
     * @param returnValue 返回值
     * @return 解密后的数据
     * @throws NoSuchFieldException   找不到成员字段异常
     * @throws IllegalAccessException 成员字段访问权限异常
     */
    public static Object decrypt(Method method, Object returnValue) throws NoSuchFieldException, IllegalAccessException {
        if (method.isAnnotationPresent(Encrypt.class)) {
            Encrypt encrypt = method.getAnnotation(Encrypt.class);
            returnValue = enOrDecrypt(returnValue, encrypt, false);
        }
        return returnValue;
    }

    /**
     * 入参加密
     *
     * @param parameters  入参类型数据
     * @param paramValues 入参值
     * @throws IllegalAccessException 成员字段访问权限异常
     * @throws NoSuchFieldException   找不到成员字段异常
     */
    public static void encrypt(Parameter[] parameters, Object[] paramValues) throws IllegalAccessException, NoSuchFieldException {
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object arg = paramValues[i];
            if (parameter.isAnnotationPresent(Encrypt.class)) {
                Encrypt encrypt = parameter.getAnnotation(Encrypt.class);
                paramValues[i] = enOrDecrypt(arg, encrypt, true);
            }
        }
    }

    /**
     * 加密或解密
     *
     * @param argValue  原对象
     * @param encrypt   加密算法注解信息
     * @param isEncrypt true表示加密；false表示解密
     * @return 加密或解密后的对象
     * @throws IllegalAccessException 成员字段访问权限异常
     * @throws NoSuchFieldException   找不到成员字段异常
     */
    private static Object enOrDecrypt(Object argValue, Encrypt encrypt, boolean isEncrypt) throws IllegalAccessException, NoSuchFieldException {
        Class<?> argValueType = argValue.getClass();
        if (argValueType.isPrimitive()) {
            throw new BaseException("不支持加密基础类型");
        } else if (String.class.isAssignableFrom(argValueType)) {
            String targetName = encrypt.targetName();
            Class<?> targetType = encrypt.targetType();
            // String参数需要指定实体类，否则无法判断是否需要加密
            if (Void.class.isAssignableFrom(targetType)) {
                throw new BaseException("加解密参数为String时必须指定targetType");
            }
            // String参数需要指定实体类里字段，否则无法判断是否需要加密
            if (ObjectUtils.isEmpty(targetName)) {
                throw new BaseException("加解密参数为String时必须指定targetName");
            }
            // 如果配置项里没有配置，则跳过加解密
            if (!encryptConfig.needEncrypt(targetType, targetName)) {
                return argValue;
            }
            argValue = encrypt.encryptType().encrypt(argValue.toString(), encryptConfig);
        } else if (Map.class.isAssignableFrom(argValueType)) {
            // 这种场景下，map的key要与实体类的字段名保持一致
            Class<?> targetType = encrypt.targetType();
            if (Void.class.isAssignableFrom(targetType)) {
                throw new BaseException("加密参数为map时必须指定targetType");
            }
            entityFieldEncryptDecrypt((Map<String, Object>) argValue, targetType, isEncrypt);
        } else if (List.class.isAssignableFrom(argValueType) || Set.class.isAssignableFrom(argValueType)) {
            String targetName = encrypt.targetName();
            Class<?> targetType = encrypt.targetType();
            // 集合参数需要指定实体类，否则无法判断是否需要加密
            if (Void.class.isAssignableFrom(targetType)) {
                throw new BaseException("加解密参数为list/set时必须指定targetType");
            }
            // 集合参数需要指定实体类里字段，否则无法判断是否需要加密
            if (ObjectUtils.isEmpty(targetName)) {
                throw new BaseException("加解密参数为list/set时必须指定targetName");
            }
            entityFieldEncryptDecrypt((Collection<Object>) argValue, targetType, targetName, isEncrypt);
        } else {
            Class<?> targetType = encrypt.targetType();
            // 简化注解字段填写，当直接使用实体类作为入参时，可以不指定targetType
            if (Void.class.isAssignableFrom(targetType)) {
                targetType = argValueType;
            }
            if (!targetType.isAssignableFrom(argValueType)) {
                throw new BaseException("加解密参数为自定义实体类时需要指定targetType");
            }
            entityFieldEncryptDecrypt(argValue, targetType, isEncrypt);
        }
        return argValue;
    }

    /**
     * 对实体类对象加密或解密
     *
     * @param o          实体类对象
     * @param targetType 实体类类型
     * @param isEncrypt  true表示加密；false表示解密
     * @throws IllegalAccessException 成员变量访问权限异常
     */
    private static void entityFieldEncryptDecrypt(Object o, Class<?> targetType, boolean isEncrypt) throws IllegalAccessException {
        Field[] declaredFields = targetType.getDeclaredFields();
        String enOrDecryptStr;
        for (Field field : declaredFields) {
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (encrypt != null
                    && encryptConfig.needEncrypt(targetType, field.getName())) {
                field.setAccessible(true);
                Object value = field.get(o);
                if (ObjectUtils.isEmpty(value)) {
                    continue;
                }
                if (isEncrypt) {
                    enOrDecryptStr = encrypt.encryptType().encrypt(value.toString(), encryptConfig);
                } else {
                    enOrDecryptStr = encrypt.encryptType().decrypt(value.toString(), encryptConfig);
                }
                field.set(o, enOrDecryptStr);
                field.setAccessible(false);
            }
        }
    }

    /**
     * 对map类型参数加密或解密
     *
     * @param map        待处理数据
     * @param targetType 对应的实体类类型
     * @param isEncrypt  true表示加密；false表示解密
     * @throws IllegalAccessException 成员变量访问权限异常
     */
    private static void entityFieldEncryptDecrypt(Map<String, Object> map, Class<?> targetType, boolean isEncrypt) throws IllegalAccessException {
        Gson gson = new GsonBuilder().create();
        Object o = gson.fromJson(gson.toJson(map), targetType);
        Field[] declaredFields = targetType.getDeclaredFields();
        String enOrDecryptStr;
        for (Field field : declaredFields) {
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (encrypt != null
                    && encryptConfig.needEncrypt(targetType, field.getName())) {
                field.setAccessible(true);
                Object value = field.get(o);
                field.setAccessible(false);
                if (ObjectUtils.isEmpty(value)) {
                    continue;
                }
                if (isEncrypt) {
                    enOrDecryptStr = encrypt.encryptType().encrypt(value.toString(), encryptConfig);
                } else {
                    enOrDecryptStr = encrypt.encryptType().decrypt(value.toString(), encryptConfig);
                }
                String fieldName = field.getName();
                map.put(fieldName, enOrDecryptStr);
            }
        }
    }

    /**
     * 对集合数据加密或解密
     *
     * @param collection 明文数据集合
     * @param targetType 对应的实体类类型
     * @param targetName 对应实体类中的字段
     * @param isEncrypt  true表示加密；false表示解密
     * @throws NoSuchFieldException 找不到字段异常
     */
    private static void entityFieldEncryptDecrypt(Collection<Object> collection, Class<?> targetType, String targetName, boolean isEncrypt) throws NoSuchFieldException {
        Field field = targetType.getDeclaredField(targetName);
        Encrypt encrypt = field.getAnnotation(Encrypt.class);
        // 字段没加注解，或者配置项里没有配置，则跳过加解密
        if (encrypt == null
                || !encryptConfig.needEncrypt(targetType, field.getName())) {
            return;
        }
        String enOrDecryptStr;
        Collection<String> enOrDecryptCollection = collection instanceof List ? new ArrayList<>(collection.size()) : new HashSet<>(collection.size());
        for (Object item : collection) {
            if (isEncrypt) {
                enOrDecryptStr = encrypt.encryptType().encrypt(item.toString(), encryptConfig);
            } else {
                enOrDecryptStr = encrypt.encryptType().decrypt(item.toString(), encryptConfig);
            }
            enOrDecryptCollection.add(enOrDecryptStr);
        }
        collection.removeAll(collection);
        collection.addAll(enOrDecryptCollection);
    }
}
package com.tomshidi.mybatis.wrappers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.tomshidi.base.encrypt.annotation.Encrypt;
import com.tomshidi.base.encrypt.helper.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 对实体字段动态加密
 * @author tomshidi
 * @since 2023/3/28 13:53
 */
public class EncryptQueryWrapper<T> extends QueryWrapper<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptQueryWrapper.class);

    @Override
    protected QueryWrapper<T> addCondition(boolean condition, String column, SqlKeyword sqlKeyword, Object val) {
        Class<?> instantiatedClass = getEntityClass();
        if (instantiatedClass == null) {
            return super.addCondition(condition, column, sqlKeyword, val);
        }
        try {
            Field field = instantiatedClass.getDeclaredField(column);
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (encrypt == null) {
                return super.addCondition(condition, column, sqlKeyword, val);
            }
            // 判断加密配置项中有没有目标类型
            if (SecurityHelper.needEncrypt(instantiatedClass, column)) {
                val = encrypt.encryptType().encrypt(val.toString(), SecurityHelper.encryptConfig);
            } else if (List.class.isAssignableFrom(val.getClass()) || Set.class.isAssignableFrom(val.getClass())) {
                Collection<Object> collection = (Collection<Object>) val;
                if (ObjectUtils.isEmpty(collection)) {
                    return super.addCondition(condition, column, sqlKeyword, val);
                }
                Object next = collection.iterator().next();
                if (next instanceof String) {
                    SecurityHelper.entityFieldEncryptDecrypt(collection, instantiatedClass, column, true);
                }
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("反射待获取字段不存在：{}", column);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error("反射访问字段权限错误：{}", column);
            throw new RuntimeException(e);
        }
        return super.addCondition(condition, column, sqlKeyword, val);
    }

    @Override
    public QueryWrapper<T> in(boolean condition, String column, Collection<?> coll) {
        encryptParam(column, coll);
        return super.in(condition, column, coll);
    }

    @Override
    public QueryWrapper<T> in(boolean condition, String column, Object... values) {
        List<Object> coll = Stream.of(values).collect(Collectors.toList());
        encryptParam(column, coll);
        return super.in(condition, column, values);
    }

    @Override
    public QueryWrapper<T> notIn(boolean condition, String column, Collection<?> coll) {
        encryptParam(column, coll);
        return super.notIn(condition, column, coll);
    }

    @Override
    public QueryWrapper<T> notIn(boolean condition, String column, Object... values) {
        List<Object> coll = Stream.of(values).collect(Collectors.toList());
        encryptParam(column, coll);
        return super.notIn(condition, column, values);
    }

    private void encryptParam(String column, Collection<?> coll) {
        Class<?> instantiatedClass = getEntityClass();
        String fieldName = column;
        Collection<Object> collection = (Collection<Object>) coll;
        if (!ObjectUtils.isEmpty(collection)) {
            Object next = collection.iterator().next();
            if (next instanceof String) {
                try {
                    SecurityHelper.entityFieldEncryptDecrypt(collection, instantiatedClass, fieldName, true);
                } catch (NoSuchFieldException e) {
                    LOGGER.error("反射待获取字段不存在：{}", fieldName);
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    LOGGER.error("反射访问字段权限异常：{}", fieldName);
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
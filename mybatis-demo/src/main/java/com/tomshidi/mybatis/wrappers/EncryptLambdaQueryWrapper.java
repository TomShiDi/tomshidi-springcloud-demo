package com.tomshidi.mybatis.wrappers;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.LambdaMeta;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.tomshidi.base.encrypt.annotation.Encrypt;
import com.tomshidi.base.encrypt.helper.SecurityHelper;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 自定义wrapper类型
 * wrapper类型参数加密
 * @author tomshidi
 * @since 2023/3/27 11:34
 */
public class EncryptLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptLambdaQueryWrapper.class);

    @Override
    protected LambdaQueryWrapper<T> addCondition(boolean condition, SFunction<T, ?> column, SqlKeyword sqlKeyword, Object val) {
        LambdaMeta meta = LambdaUtils.extract(column);
        String fieldName = PropertyNamer.methodToProperty(meta.getImplMethodName());
        Class<?> instantiatedClass = meta.getInstantiatedClass();
        try {
            Field field = instantiatedClass.getDeclaredField(fieldName);
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (encrypt != null) {
                // 判断加密配置项中有没有目标类型
                if (SecurityHelper.needEncrypt(instantiatedClass, fieldName)) {
                    val = encrypt.encryptType().encrypt(val.toString(), SecurityHelper.encryptConfig);
                }
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("反射待获取字段不存在：{}", fieldName);
            throw new RuntimeException(e);
        }
        return super.addCondition(condition, column, sqlKeyword, val);
    }

    @Override
    public LambdaQueryWrapper<T> in(boolean condition, SFunction<T, ?> column, Collection<?> coll) {
        encryptParam(column, coll);
        return super.in(condition, column, coll);
    }

    @Override
    public LambdaQueryWrapper<T> in(boolean condition, SFunction<T, ?> column, Object... values) {
        List<Object> collection = Stream.of(values).collect(Collectors.toList());
        encryptParam(column, collection);
        return super.in(condition, column, values);
    }

    @Override
    public LambdaQueryWrapper<T> notIn(boolean condition, SFunction<T, ?> column, Collection<?> coll) {
        encryptParam(column, coll);
        return super.notIn(condition, column, coll);
    }

    @Override
    public LambdaQueryWrapper<T> notIn(boolean condition, SFunction<T, ?> column, Object... values) {
        List<Object> collection = Stream.of(values).collect(Collectors.toList());
        encryptParam(column, collection);
        return super.notIn(condition, column, values);
    }

    private void encryptParam(SFunction<T, ?> column, Collection<?> coll) {
        LambdaMeta meta = LambdaUtils.extract(column);
        String fieldName = PropertyNamer.methodToProperty(meta.getImplMethodName());
        Class<?> instantiatedClass = meta.getInstantiatedClass();
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
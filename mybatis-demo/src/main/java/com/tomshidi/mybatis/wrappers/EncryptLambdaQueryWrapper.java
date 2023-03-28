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

import java.lang.reflect.Field;

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
        // 仅支持加密String类型
        if (!(val instanceof String)) {
            return super.addCondition(condition, column, sqlKeyword, val);
        }
        LambdaMeta meta = LambdaUtils.extract(column);
        String fieldName = PropertyNamer.methodToProperty(meta.getImplMethodName());
        Class<?> instantiatedClass = meta.getInstantiatedClass();
        try {
            Field field = instantiatedClass.getDeclaredField(fieldName);
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (encrypt != null) {
                // 判断加密配置项中有没有目标类型
                if (SecurityHelper.needEncrypt(meta.getInstantiatedClass(), fieldName)) {
                    val = encrypt.encryptType().encrypt(val.toString(), SecurityHelper.encryptConfig);
                }
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("反射待获取字段不存在：{}", fieldName);
            throw new RuntimeException(e);
        }
        return super.addCondition(condition, column, sqlKeyword, val);
    }
}

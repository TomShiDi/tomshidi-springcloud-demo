package com.tomshidi.mybatis.wrappers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.tomshidi.base.encrypt.annotation.Encrypt;
import com.tomshidi.base.encrypt.helper.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 对实体字段动态加密
 * @author tangshili
 * @since 2023/3/28 13:53
 */
public class EncryptQueryWrapper<T> extends QueryWrapper<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptQueryWrapper.class);

    @Override
    protected QueryWrapper<T> addCondition(boolean condition, String column, SqlKeyword sqlKeyword, Object val) {
        // 仅支持加密String类型
        if (!(val instanceof String)) {
            return super.addCondition(condition, column, sqlKeyword, val);
        }
        Class<?> instantiatedClass = getEntityClass();
        if (instantiatedClass == null) {
            return super.addCondition(condition, column, sqlKeyword, val);
        }
        try {
            Field field = instantiatedClass.getDeclaredField(column);
            Encrypt encrypt = field.getAnnotation(Encrypt.class);
            if (encrypt != null) {
                // 判断加密配置项中有没有目标类型
                if (SecurityHelper.needEncrypt(instantiatedClass, column)) {
                    val = encrypt.encryptType().encrypt(val.toString(), SecurityHelper.encryptConfig);
                }
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("反射待获取字段不存在：{}", column);
            throw new RuntimeException(e);
        }
        return super.addCondition(condition, column, sqlKeyword, val);
    }
}

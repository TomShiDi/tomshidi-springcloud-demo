package com.tomshidi.base.encrypt.annotation;

import com.tomshidi.base.encrypt.enums.EncryptEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tomshidi
 * @since 2023/3/22 17:47
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface Encrypt {

    /**
     * 加密类型
     *
     * @return EncryptEnum
     * @see EncryptEnum
     */
    EncryptEnum encryptType() default EncryptEnum.SM4;

    /**
     * 映射的字段名
     *
     * @return String
     */
    String targetName() default "";

    /**
     * 参数绑定的实体类
     *
     * @return Class
     */
    Class<?> targetType() default Void.class;

    /**
     * 加密或解密
     *
     * @return true表示加密；false表示解密
     */
    boolean enOrDecrypt() default true;
}

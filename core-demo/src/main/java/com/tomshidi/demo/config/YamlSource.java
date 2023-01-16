package com.tomshidi.demo.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;

/**
 * @author tomshidi
 * @date 2021/12/13 16:28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface YamlSource {
    /**
     * yaml配置文件路径
     * @return 路径
     */
    String path();

    /**
     * 字符编码，默认UTF-8
     * @see StandardCharsets#UTF_8 etc.
     * @return 字符编码
     */
    String encoding() default "UTF-8";
}

package com.tomshidi.demo.config;

import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tomshidi
 * @date 2023/1/29 15:10
 */
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer.setUseSuffixPatternMatch()
    }
}

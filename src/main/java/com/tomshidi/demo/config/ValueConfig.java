package com.tomshidi.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * @author tomshidi
 * @date 2022/12/8 17:44
 */
@Component
@RefreshScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ValueConfig {

    @Value("${test-config.name:}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

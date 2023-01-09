package com.tomshidi.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Nacos支持@Value属性自动刷新，需要添加@RefreshScope注解
 * 此时，只能通过get方法获取动态变化后的值，因为使用的是动态代理实现
 * @author tomshidi
 * @date 2023/1/6 17:38
 */
@Component
@RefreshScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NacosRefreshableConfig {

    @Value("${test-config.name:}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

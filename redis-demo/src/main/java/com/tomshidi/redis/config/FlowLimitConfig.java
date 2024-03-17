package com.tomshidi.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author TomShiDi
 * @since 2024/3/17 15:05
 */
@Component
@ConfigurationProperties(prefix = "tomshidi.flow-limit")
public class FlowLimitConfig {
    private String limitKeyPrefix = "limit_";

    private Double rate = 1.0;

    private Integer capacity = 10;

    public String getLimitKeyPrefix() {
        return limitKeyPrefix;
    }

    public void setLimitKeyPrefix(String limitKeyPrefix) {
        this.limitKeyPrefix = limitKeyPrefix;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}

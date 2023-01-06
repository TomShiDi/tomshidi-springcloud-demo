package com.tomshidi.demo.config;

import com.tomshidi.demo.proxy.CgProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * @author tomshidi
 * @date 2021/8/4 9:40
 */
@Component
@ConfigurationProperties(prefix = "tomshidi")
public class TestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConfig.class);

    private Duration executionTime;

    private Map<String, List<User>> userMap;

    public Map<String, List<User>> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, List<User>> userMap) {
        this.userMap = userMap;
    }

    public Duration getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Duration executionTime) {
        this.executionTime = executionTime;
    }

    public static class User {

        private String id;

        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public void logCookie() {
        Cookie[] cookies = (Cookie[]) CgProxyFactory.cookieThreadLocal.get();
        for (Cookie cookie : cookies) {
            LOGGER.info("【{}】 {}={}", Thread.currentThread().getName(), cookie.getName(), cookie.getValue());
        }
    }
}

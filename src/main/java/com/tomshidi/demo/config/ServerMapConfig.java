package com.tomshidi.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author tomshidi
 * @date 2022/8/16 10:04
 */
@Component
//@YamlSource(path = "config/server-map.yml")
@PropertySource("classpath:config/server-map.properties")
@ConfigurationProperties(prefix = "server-map")
@ConditionalOnProperty(name = "server-map.enabled", matchIfMissing = false)
public class ServerMapConfig {
    private Map<String, String> oldNewNameMap;

    public void setOldNewNameMap(Map<String, String> oldNewNameMap) {
        this.oldNewNameMap = oldNewNameMap;
    }

    public String queryMappingServerName(String originServerName) {
        if (ObjectUtils.isEmpty(oldNewNameMap)) {
            return "";
        }
        return oldNewNameMap.get(originServerName);
    }
}

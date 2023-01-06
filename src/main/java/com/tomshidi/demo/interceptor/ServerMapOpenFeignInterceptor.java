package com.tomshidi.demo.interceptor;

import com.tomshidi.demo.config.ServerMapConfig;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * feign调用时服务名动态映射拦截器
 * 用于服务整合后，原服务到新服务名的映射
 *
 * @author tomshidi
 * @date 2022/8/16 9:58
 */
@Component
@ConditionalOnMissingBean(value = ServerMapInterceptor.class, ignored = ServerMapInterceptor.class)
@ConditionalOnBean(ServerMapConfig.class)
@Order(Ordered.LOWEST_PRECEDENCE)
public class ServerMapOpenFeignInterceptor implements RequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMapInterceptor.class);

    private static final String HEADER_ORIGIN_SERVER_NAME = "Origin-Server-Name";

    private ServerMapConfig serverMapConfig;

    @Override
    public void apply(RequestTemplate template) {
        String host;
        String mappingServerName;
        try {
            URI requestUri = new URI(template.feignTarget().url());
            host = requestUri.getHost();
            if (ObjectUtils.isEmpty(mappingServerName = serverMapConfig.queryMappingServerName(host))) {
                return;
            }
            // 将原始服务名放到header中
            template.header(HEADER_ORIGIN_SERVER_NAME, host);
            String mappingUrl = requestUri.toString().replace("//" + host, "//" + mappingServerName);
            template.target(mappingUrl);
        } catch (URISyntaxException e) {
            LOGGER.error("feign调用服务名映射失败，请求数据为" + template.feignTarget().toString(), e);
        }
    }

    @Autowired
    public void setServerMapConfig(ServerMapConfig serverMapConfig) {
        this.serverMapConfig = serverMapConfig;
        LOGGER.info("feign调用服务动态映射组件注册成功");
    }
}


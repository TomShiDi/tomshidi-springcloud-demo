package com.tomshidi.demo.interceptor;

import com.tomshidi.demo.config.ServerMapConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * RestTemplate调用动态映射服务名
 * @author tomshidi
 * @date 2022/8/16 9:58
 */
@Component
@ConditionalOnBean(ServerMapConfig.class)
@Order(Ordered.LOWEST_PRECEDENCE)
public class ServerMapInterceptor implements ClientHttpRequestInterceptor {

    private ServerMapConfig serverMapConfig;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        URI requestUri = request.getURI();
        String host = requestUri.getHost();
        String mappingServerName;
        if (ObjectUtils.isEmpty(mappingServerName = serverMapConfig.queryMappingServerName(host))) {
            return execution.execute(request, body);
        }
        try {
            String mappingUrl = requestUri.toString().replace("//" + host, "//" + mappingServerName);
            URI mappingUri = new URI(mappingUrl);
            Field uriField = request.getClass().getDeclaredField("uri");
            uriField.setAccessible(true);
            uriField.set(request, mappingUri);
        } catch (NoSuchFieldException | IllegalAccessException | URISyntaxException e) {
            e.printStackTrace();
        }
        return execution.execute(request, body);
    }

    @Autowired
    public void setServerMapConfig(ServerMapConfig serverMapConfig) {
        this.serverMapConfig = serverMapConfig;
    }
}

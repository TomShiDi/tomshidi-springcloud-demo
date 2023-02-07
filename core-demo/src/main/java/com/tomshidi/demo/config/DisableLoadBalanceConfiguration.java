package com.tomshidi.demo.config;

import feign.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign远程请求发起客户端，不使用负载均衡
 * @author tomshidi
 * @date 2023/1/12 15:50
 */
@Configuration
public class DisableLoadBalanceConfiguration {

    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }
}

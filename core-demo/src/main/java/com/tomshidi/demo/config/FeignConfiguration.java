package com.tomshidi.demo.config;

import feign.Contract;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tangshili
 * @date 2023/1/11 17:57
 */
@Configuration
@EnableFeignClients(basePackages = "com.tomshidi.demo.client")
public class FeignConfiguration {

    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }
}

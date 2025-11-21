package com.tomshidi.aidemo.helloworld.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaaLLMConfig {

    /**
     * DashScope API Key
     */
    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    /**
     * DashScope API Bean
     */
    @Bean
    public DashScopeApi dashScopeApi(){
        return DashScopeApi.builder()
                .apiKey(apiKey)
                .build();
    }
}

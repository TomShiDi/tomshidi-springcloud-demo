package com.tomshidi.aidemo.todomenu.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfiguration {

    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder()
                .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                .workSpaceId("llm-ulnkq547xrut3xr0")
                .build();
    }

    /**
     * ChatClient
     *
     * @param chatModel model
     * @return ChatClient 实例
     */
    @Bean
    public ChatClient chatClient(ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .build();
    }
}

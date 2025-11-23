package com.tomshidi.aidemo.baidumcp.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfiguration {

    /**
     * 配置支持工具调用的 ChatClient
     *
     * @param chatModel            聊天模型
     * @param toolCallbackProvider 工具回调提供者
     * @return ChatClient 实例
     */
    @Bean
    public ChatClient chatClient(ChatModel chatModel, ToolCallbackProvider toolCallbackProvider) {
        return ChatClient.builder(chatModel)
                .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
                .build();
    }
}

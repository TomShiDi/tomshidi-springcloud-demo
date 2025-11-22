package com.tomshidi.aidemo.chatpersistent.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatMemory4Redis")
public class ChatMemory4RedisController {

    @Resource(name = "qwenChatClient")
    private ChatClient qwenClient;

    /**
     * 基于 Redis 实现的持久化聊天记忆示例
     *
     * @param msg    用户输入消息
     * @param userId 用户 ID，用于区分不同用户的聊天记忆
     * @return 模型回复内容
     */
    @GetMapping("/chat")
    public String chat(@RequestParam(name = "msg") String msg,
                       @RequestParam(name = "userId") String userId) {
        return qwenClient.prompt(msg)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, userId))
                .call()
                .content();
    }
}

package com.tomshidi.aidemo.prompt.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/prompt")
public class PromptController {

    /**
     * Text Chat Model Client
     */
    @Resource(name = "deepseek")
    private ChatModel deepseekChatModel;

    /**
     * Text Chat Model Client
     */
    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    /**
     * origin call
     *
     * @param question input message
     * @return response message stream
     */
    @GetMapping("/stream/origin")
    public Flux<ChatResponse> chat(@RequestParam(name = "msg", defaultValue = "你好") String question) {
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事助手，每个故事300字以内。");
        UserMessage userMessage = new UserMessage(question);
        Prompt prompt = Prompt.builder().messages(systemMessage, userMessage).build();
        return deepseekChatModel.stream(prompt);
    }

    /**
     * origin call
     *
     * @param question input message
     * @return response message stream
     */
    @GetMapping("/stream/text")
    public Flux<String> chatString(@RequestParam(name = "msg", defaultValue = "你好") String question) {
        SystemMessage systemMessage = new SystemMessage("你是一个讲故事助手，每个故事300字以内。");
        UserMessage userMessage = new UserMessage(question);
        Prompt prompt = Prompt.builder().messages(systemMessage, userMessage).build();
        return deepseekChatModel.stream(prompt)
                .mapNotNull(r->r.getResults().getFirst().getOutput().getText());
    }

    @GetMapping("/sync/chat")
    public String chatSync(@RequestParam(name = "msg", defaultValue = "你好") String question) {
        AssistantMessage assistantMessage = qwenChatClient.prompt()
                .user(question)
                .call()
                .chatResponse()
                .getResult()
                .getOutput();
        return assistantMessage.getText();
    }

    /**
     * stream call
     *
     * @param question input message
     * @return response message stream
     */
    @GetMapping("/streamChat")
    public Flux<String> stream(@RequestParam(name = "msg", defaultValue = "你好") String question) {
        return qwenChatClient.prompt()
                // AI能力边界定义
                .system("你是一个法律助手，只回答法律问题。其他问题一律回答“对不起，我只能回答法律相关的问题”。")
                .user(question)
                .stream()
                .content();
    }
}

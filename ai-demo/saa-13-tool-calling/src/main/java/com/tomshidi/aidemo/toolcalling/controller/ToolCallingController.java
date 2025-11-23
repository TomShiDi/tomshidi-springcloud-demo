package com.tomshidi.aidemo.toolcalling.controller;

import com.tomshidi.aidemo.toolcalling.tools.DateTimeTools;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Tool Calling
 */
@RestController
@RequestMapping("/toolCalling")
public class ToolCallingController {

    @Resource
    private ChatModel chatModel;

    @Resource
    private ChatClient chatClient;

    /**
     * 普通问答接口
     *
     * @param msg 用户问题
     * @return 回答
     */
    @GetMapping("/chat")
    public String chat(@RequestParam(name = "msg",defaultValue = "你是谁，现在几点了？") String msg) {
        ToolCallback[] tools = ToolCallbacks.from(new DateTimeTools());
        ToolCallingChatOptions options = ToolCallingChatOptions.builder().toolCallbacks(tools).build();
        Prompt prompt = new Prompt(msg, options);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

    /**
     * 流式问答接口
     *
     * @param msg 用户问题
     * @return 回答
     */
    @GetMapping("/streamChat")
    public Flux<String> streamChat(@RequestParam(name = "msg",defaultValue = "你是谁，现在几点了？") String msg) {
        return chatClient.prompt(msg)
                .tools(new DateTimeTools())
                .stream()
                .content();
    }
}

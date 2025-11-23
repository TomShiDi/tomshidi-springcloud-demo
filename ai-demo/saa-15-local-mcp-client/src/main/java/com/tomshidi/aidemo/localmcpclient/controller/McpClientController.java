package com.tomshidi.aidemo.localmcpclient.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/mcpClient")
public class McpClientController {

    @Resource
    private ChatClient chatClient;

    @Resource
    private ChatModel chatModel;

    /**
     * 流式问答接口，使用Client调用了MCP服务
     *
     * @param msg 用户问题
     * @return 回答
     */
    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(name = "msg", defaultValue = "北京") String msg) {
        return chatClient.prompt(msg).stream().content();
    }

    /**
     * 流式问答接口，直接使用Model调用了MCP服务
     *
     * @param msg 用户问题
     * @return 回答
     */
    @GetMapping("/nonMcpChat")
    public Flux<String> nonMcpChat(@RequestParam(name = "msg", defaultValue = "北京") String msg) {
        return chatModel.stream(msg);
    }

}

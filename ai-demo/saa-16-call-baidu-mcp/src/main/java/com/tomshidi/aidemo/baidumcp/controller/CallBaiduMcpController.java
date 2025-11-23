package com.tomshidi.aidemo.baidumcp.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/baiduMcp")
public class CallBaiduMcpController {

    @Resource
    private ChatClient chatClient;

    @Resource
    private ChatModel chatModel;


    /**
     * 添加了百度地图mcp能力的接口
     *
     * @return 响应
     */
    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(name = "msg") String msg) {
        return chatClient.prompt(msg).stream().content();
    }

    /**
     * 未添加百度地图mcp能力的接口
     *
     * @return 响应
     */
    @GetMapping("/nonMcpChat")
    public Flux<String> nonMcpChat(@RequestParam(name = "msg") String msg) {
        return chatModel.stream(msg);
    }
}

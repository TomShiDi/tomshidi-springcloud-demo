package com.tomshidi.aidemo.ollama.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ollama")
public class OllamaController {

    /**
     * Text Chat Model Client
     */
    @Resource(name = "ollamaChatModel")
    private ChatModel chatModel;

    /**
     * normal call
     *
     * @param msg input message
     * @return response message
     */
    @GetMapping("/doChat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你好") String msg) {
        return chatModel.call(msg);
    }

    /**
     * stream call
     *
     * @param msg input message
     * @return response message stream
     */
    @GetMapping("/streamChat")
    public Flux<String> stream(@RequestParam(name = "msg", defaultValue = "你好") String msg) {
        return chatModel.stream(msg);
    }

}

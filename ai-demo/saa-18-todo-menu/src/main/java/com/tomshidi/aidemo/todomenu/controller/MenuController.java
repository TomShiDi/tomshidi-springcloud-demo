package com.tomshidi.aidemo.todomenu.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private ChatModel chatModel;

    /**
     * 家常菜推荐，不使用阿里云生态
     *
     * @param question 用户问题
     * @return 家常菜推荐结果
     */
    @GetMapping("/eat")
    public Flux<String> eat(@RequestParam(name = "msg", defaultValue = "今天吃什么") String question) {
        String sysMsg = "你是一名AI厨师助手，每次随机生成三个家常菜，并提供这些家常菜的详细做法步骤，以HTML格式返回，字数控制在1500字以内。";

        SystemMessage systemMessage = new SystemMessage(sysMsg);
        UserMessage userMessage = new UserMessage(question);
        Prompt prompt = new Prompt(systemMessage, userMessage);
        return chatModel.stream(prompt)
                .map(r -> r.getResults().getFirst().getOutput().getText());
    }
}

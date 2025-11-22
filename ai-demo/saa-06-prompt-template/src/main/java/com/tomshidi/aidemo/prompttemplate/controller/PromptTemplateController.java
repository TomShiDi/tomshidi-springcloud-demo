package com.tomshidi.aidemo.prompttemplate.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.Serializable;
import java.util.Map;

@RestController
@RequestMapping("/promptTemplate")
public class PromptTemplateController {

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

    @Resource(name = "deepseekChatClient")
    private ChatClient deepseekChatClient;

    @Value("classpath:prompt-template/user-template.txt")
    private org.springframework.core.io.Resource userTemplate;

    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(name = "topic") String topic,
                             @RequestParam(name = "outputFormat") String outputFormat,
                             @RequestParam(name = "wordCount") Integer wordCount) {
        String template = """
                讲一个关于{topic}的故事。
                并以{outputFormat}的格式输出。
                故事内容大约在{wordCount}字左右。
                """;
        Map<String, Object> paramMap = Map.of("topic", topic, "outputFormat", outputFormat, "wordCount", wordCount);
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(paramMap);
        return qwenChatClient.prompt(prompt)
                .stream()
                .content();
    }

    /**
     * 使用外部文件作为Prompt模板
     *
     * @param topic        话题
     * @param outputFormat 输出格式
     * @return Flux<String>
     */
    @GetMapping("/file/chat")
    public Flux<String> chat2(@RequestParam(name = "topic") String topic,
                              @RequestParam(name = "outputFormat") String outputFormat) {
        Map<String, Object> paramMap = Map.of("topic", topic, "outputFormat", outputFormat);
        PromptTemplate promptTemplate = new PromptTemplate(userTemplate);
        Prompt prompt = promptTemplate.create(paramMap);
        return qwenChatClient.prompt(prompt)
                .stream()
                .content();
    }

    /**
     * 使用系统提示词模板和用户提示词模板
     *
     * @param sysTopic 系统话题
     * @param userTopic 用户话题
     * @return Flux<String>
     */
    @GetMapping("/role/chat")
    public Flux<String> chat3(@RequestParam(name = "sysTopic") String sysTopic,
                              @RequestParam(name = "userTopic") String userTopic) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate("你是{systemTopic}助手，只回答与{systemTopic}相关的问题，以HTML格式输出。");
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("systemTopic", sysTopic));
        PromptTemplate userPromptTemplate = new PromptTemplate("解释一下{userTopic}");
        Message userMessage = userPromptTemplate.createMessage(Map.of("userTopic", userTopic));
        Prompt prompt = new Prompt(systemMessage, userMessage);
        return deepseekChatClient.prompt(prompt)
                .stream()
                .content();
    }
}

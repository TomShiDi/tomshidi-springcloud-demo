package com.tomshidi.aidemo.bailianrag.controller;

import com.alibaba.cloud.ai.advisor.DocumentRetrievalAdvisor;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bailianRrag")
public class BailianRagController {

    @Resource
    private ChatClient chatClient;

    @Resource
    private DashScopeApi dashScopeApi;

    /**
     * 基于阿里百炼平台RAG的聊天接口
     *
     * @param message 用户输入消息
     * @return 聊天响应流
     */
    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(name = "msg") String message) {
        DashScopeDocumentRetriever dashScopeDocumentRetriever = new DashScopeDocumentRetriever(dashScopeApi, DashScopeDocumentRetrieverOptions.builder().withIndexName("ops").build());
        return chatClient.prompt()
                .user(message)
                .advisors(new DocumentRetrievalAdvisor(dashScopeDocumentRetriever))
                .stream()
                .content();
    }
}

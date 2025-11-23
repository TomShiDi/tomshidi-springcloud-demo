package com.tomshidi.aidemo.rag4aiops.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/rag4aiops")
public class Rag4AiopsController {

    @Resource(name = "qwenChatClient")
    private ChatClient qwenChatClient;

    @Resource
    private VectorStore vectorStore;

    /**
     * RAG问答接口
     *
     * @param msg 用户问题
     * @return 回答
     */
    @GetMapping("/rag")
    public Flux<String> rag(@RequestParam(name = "msg") String msg) {
        String systemInfo = "你是一个运维专家，按照编码给出对应故障解释，否则回复：找不到故障信息。";

        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).build())
                .build();
        return qwenChatClient.prompt()
                .system(systemInfo)
                .user(msg)
                .advisors(retrievalAugmentationAdvisor)
                .stream()
                .content();
    }

}

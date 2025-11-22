package com.tomshidi.aidemo.embed2vector.controller;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingOptions;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/embed2vector")
public class Embed2VectorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Embed2VectorController.class);

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;

    /**
     * 文本转向量
     *
     * @param msg 输入文本
     * @return 向量结果
     */
    @GetMapping("/text2embed")
    public EmbeddingResponse text2Embed(@RequestParam(name = "msg") String msg) {
        EmbeddingResponse embeddingResponse = embeddingModel.call(new EmbeddingRequest(List.of(msg), DashScopeEmbeddingOptions.builder().withModel("text-embedding-v3").build()));
        LOGGER.info("EmbeddingResponse: {}", embeddingResponse.getResult().getOutput());
        return embeddingResponse;
    }

    /**
     * 添加文本到向量数据库
     */
    @PostMapping("/embed2vector/add")
    public void add() {
        List<Document> documents = List.of(new Document("你好"), new Document("I love java"));
        vectorStore.add(documents);
    }

    /**
     * 向量数据库查询
     *
     * @param msg 查询文本
     * @return 查询结果
     */
    @GetMapping("/vectorstore/query")
    public List<Document> query(@RequestParam(name = "msg") String msg) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(msg)
                .topK(2)
                .build();
        List<Document> documents = vectorStore.similaritySearch(searchRequest);
        LOGGER.info("Query Documents: {}", documents);
        return documents;
    }
}

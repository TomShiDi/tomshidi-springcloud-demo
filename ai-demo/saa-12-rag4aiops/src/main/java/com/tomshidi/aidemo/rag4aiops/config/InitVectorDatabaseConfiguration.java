package com.tomshidi.aidemo.rag4aiops.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 向量数据库初始化配置
 */
@Configuration
public class InitVectorDatabaseConfiguration {
    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("classpath:ops.txt")
    private Resource opsFile;

    @PostConstruct
    public void init() {
        // 读取文件
        TextReader textReader = new TextReader(opsFile);
        textReader.setCharset(Charset.defaultCharset());

        // 文件内容转换为向量（分词）
        List<Document> documents = new TokenTextSplitter().transform(textReader.read());

        // 向量存储
//        vectorStore.add(documents);

        // 处理多次初始化的数据重复问题
        String sourceMetadata = textReader.getCustomMetadata().get("source").toString();
        String redisKey = "vector-tomshidi:" + sourceMetadata;

        Boolean result = redisTemplate.opsForValue().setIfAbsent(redisKey, "1");
        if (Boolean.TRUE.equals(result)) {
            vectorStore.add(documents);
        }
    }
}

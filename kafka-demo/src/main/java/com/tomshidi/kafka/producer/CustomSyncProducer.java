package com.tomshidi.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 生产者同步发送
 * @author TomShiDi
 * @since 2024/3/13 10:42
 */
public class CustomSyncProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.创建配置
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "node1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 2.创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        // 3. 循环发送
        for (int i = 1; i < 11; i++) {
            String message = String.format("第%d号服务员", i);
            ProducerRecord<String, String> record = new ProducerRecord<>("first", message);
            producer.send(record, (recordMetadata, ex) -> {
                if (ex == null) {
                    int partition = recordMetadata.partition();
                    long offset = recordMetadata.offset();
                    System.out.printf("数据：%s\n分区：%d\n偏移量：%d\n", message, partition, offset);
                }
            }).get();
        }
    }
}

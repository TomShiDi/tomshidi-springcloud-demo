package com.tomshidi.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author TomShiDi
 * @since 2024/3/13 21:28
 */
public class CustomConsumer {
    public static void main(String[] args) {
        // 1.创建配置
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "node1:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group01");
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        // 2.创建消费者对象
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        // 3.消费者订阅topic
        List<String> topics = Stream.of("first").collect(Collectors.toList());
        consumer.subscribe(topics);
        // 轮训拉取数据
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1L));
            for (ConsumerRecord<String, String> record : records) {
                String value = record.value();
                int partition = record.partition();
                long offset = record.offset();
                System.out.printf("数据：%s\n分区：%d\n偏移量：%d\n", value, partition, offset);
            }
            // 4.手动提交offset
            consumer.commitSync();
        }
    }
}

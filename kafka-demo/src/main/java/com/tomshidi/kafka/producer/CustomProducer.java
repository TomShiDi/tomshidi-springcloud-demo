package com.tomshidi.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author TomShiDi
 * @since 2024/3/13 10:01
 */
public class CustomProducer {
    public static void main(String[] args) {
        // 1.创建配置对象
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "node1:9092,node2:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 2.创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        // 3.发送数据
        for (int i = 1; i < 11; i++) {
            String message = String.format("第%d号服务员", i);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("first", message);
            kafkaProducer.send(producerRecord);
        }
        // 4.关闭资源
        kafkaProducer.close();
    }
}

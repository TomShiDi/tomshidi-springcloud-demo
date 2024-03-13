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
public class CustomThroughputProducer {
    public static void main(String[] args) {
        // 1.创建配置对象
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "node1:9092,node2:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.tomshidi.kafka.partitioner.CustomPartitioner");
        // 性能优化配置
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        // ack应答机制配置
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 幂等性
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        // 2.创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        // 3.发送数据
        for (int i = 1; i < 11; i++) {
            String message = String.format("第%d号服务员", i);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("first", i + "", message);
            kafkaProducer.send(producerRecord,
                    (recordMetadata, ex) -> {
                if (ex == null) {
                    int partition = recordMetadata.partition();
                    long offset = recordMetadata.offset();
                    System.out.printf("数据：%s\n分区：%d\n偏移量：%d\n", message, partition, offset);
                }
            });
        }
        // 4.关闭资源
        kafkaProducer.close();
    }
}

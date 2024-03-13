package com.tomshidi.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author TomShiDi
 * @since 2024/3/13 14:39
 */
public class CustomPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 1.计算分区号
        String keyStr = key.toString();
        int keyHash = keyStr.hashCode();
        Integer partitionCount = cluster.partitionCountForTopic(topic);
        return keyHash % partitionCount;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}

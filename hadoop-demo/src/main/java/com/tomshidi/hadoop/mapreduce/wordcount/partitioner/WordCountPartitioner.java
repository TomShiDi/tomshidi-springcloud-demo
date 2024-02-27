package com.tomshidi.hadoop.mapreduce.wordcount.partitioner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


/**
 * 自定义分区逻辑
 * @author TomShiDi
 * @since 2024/2/27 15:57
 */
public class WordCountPartitioner extends Partitioner<Text, IntWritable> {
    @Override
    public int getPartition(Text text, IntWritable intWritable, int i) {
        String value = text.toString();
        if (value == null || value.length() == 0) {
            return 0;
        }
        // 根据首字母分区
        char firstChar = value.charAt(0);
        return firstChar % 5;
    }
}

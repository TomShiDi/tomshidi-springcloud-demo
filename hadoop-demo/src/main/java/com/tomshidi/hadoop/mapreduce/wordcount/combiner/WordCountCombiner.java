package com.tomshidi.hadoop.mapreduce.wordcount.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author TomShiDi
 * @since 2023/5/25 21:31
 */
public class WordCountCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable outV = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable value : values) {
            count = count + value.get();
        }
        outV.set(count);
        context.write(key, outV);
    }
}

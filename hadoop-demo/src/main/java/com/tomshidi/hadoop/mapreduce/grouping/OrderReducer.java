package com.tomshidi.hadoop.mapreduce.grouping;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author TomShiDi
 * @since 2024/2/27 21:27
 */
public class OrderReducer extends Reducer<OrderBean, Text, Text, NullWritable> {

    @Override
    protected void reduce(OrderBean key, Iterable<Text> values, Reducer<OrderBean, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        // 因为在suffle阶段做了排序，所以同一组第一条记录的价格为最大
        Text first = values.iterator().next();
        context.write(first, NullWritable.get());
    }
}

package com.tomshidi.hadoop.mapreduce.grouping;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author TomShiDi
 * @since 2024/2/27 21:35
 */
public class OrderMapper extends Mapper<LongWritable, Text, OrderBean, Text> {

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, OrderBean, Text>.Context context) throws IOException, InterruptedException {
        String data = value.toString();
        String[] fields = data.split(" ");
        if (fields.length < 3) {
            return;
        }
        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(fields[0]);
        orderBean.setPrice(Double.valueOf(fields[2]));
        context.write(orderBean, value);
    }
}

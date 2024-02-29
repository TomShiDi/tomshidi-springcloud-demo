package com.tomshidi.hadoop.mapreduce.grouping;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author TomShiDi
 * @since 2023/5/24 15:51
 */
public class OrderDriver {

    /**
     * 数据输入、MapTask、分区（Partitioner）、排序、Combiner、分组、ReduceTask
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // 1.获取job
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration, "grouping");
        // 2.设置jar包路径
        job.setJarByClass(OrderDriver.class);
        // 3.关联mapper和reducer
        job.setMapperClass(OrderMapper.class);
        job.setReducerClass(OrderReducer.class);
        // 设置分组器
        job.setGroupingComparatorClass(DiyGrouping.class);
        // 4.设置map输出的kv类型
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(Text.class);
        // 5.设置最终输出的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        // 设置文件合并输入
        job.setInputFormatClass(CombineTextInputFormat.class);
        // 设置逻辑切分的数据块大小
        // 逻辑切片数决定MapTask的个数，并不是由物理切片block决定
        // 切片边界机制：剩余内容大小小于切片大小的10%，则剩余内容不额外切片，合并到上一个切片
        CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);
        // 6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\Personal-Projects\\tomshidi-springcloud-demo\\hadoop-demo\\src\\main\\resources\\input\\order"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\Personal-Projects\\tomshidi-springcloud-demo\\hadoop-demo\\src\\main\\resources\\output\\order"));
        // 7.提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}

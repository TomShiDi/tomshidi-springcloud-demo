package com.tomshidi.hadoop.mapreduce.wordcount;

import com.tomshidi.hadoop.mapreduce.wordcount.partitioner.WordCountPartitioner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
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
public class WordCountDriver {

    /**
     * 数据输入、MapTask、分区（Partitioner）、排序、Combiner、分组、ReduceTask
     * @param args
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // 1.获取job
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration, "wordcount");
        // 2.设置jar包路径
        job.setJarByClass(WordCountDriver.class);
        // 3.关联mapper和reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        // 4.设置map输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        // 5.设置最终输出的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // 设置文件合并输入
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);
        // 设置自定义分区类
        job.setPartitionerClass(WordCountPartitioner.class);
        // 设置Reducer个数，需要与WordCountPartitioner中的分区数对应
        job.setNumReduceTasks(5);
        // 6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("D:\\Personal-Projects\\tomshidi-springcloud-demo\\hadoop-demo\\src\\main\\resources\\input\\wordcount"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\Personal-Projects\\tomshidi-springcloud-demo\\hadoop-demo\\src\\main\\resources\\output\\wordcount"));
        // 7.提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}

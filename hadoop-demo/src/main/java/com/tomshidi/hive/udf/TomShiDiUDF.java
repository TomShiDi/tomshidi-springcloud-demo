package com.tomshidi.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * hive 自定义函数
 * 1. 上传jar包到hdfs中
 * 2. 在hive命令行终端执行 create function tomshidi_upper as 'com.tomshidi.hive.udf.TomShiDiUDF' using jar 'hdfs://node1:8020/hive-func/hadoop-demo-1.0.0.jar';
 * 3. 在hive命令行终端执行 select tomshidi_upper('hello');
 * @author TomShiDi
 * @since 2024/3/7 16:45
 */
public class TomShiDiUDF extends UDF {
    /**
     * 将输入字符串的首字母大写
     *
     * @param line 输入字符串
     * @return 字符串
     */
    public Text evaluate(Text line) {
        if (line == null || "".equals(line.toString())) {
            return line;
        }
        String s = line.toString();
        String newStr = s.substring(0, 1).toUpperCase() + s.substring(1);
        line.set(newStr);
        return line;
    }
}

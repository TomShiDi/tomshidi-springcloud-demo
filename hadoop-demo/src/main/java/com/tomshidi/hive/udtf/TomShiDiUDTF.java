package com.tomshidi.hive.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TomShiDi
 * @since 2024/3/8 9:24
 */
public class TomShiDiUDTF extends GenericUDTF {

    private final transient Object[] forwardValues = new Object[1];

    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        // 设置列名
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("column_01");

        List<ObjectInspector> fieldOIs = new ArrayList<>();
        // 设置输出的列类型
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

    @Override
    public void process(Object[] objects) throws HiveException {
        // 获取待切分的字符串
        String args = objects[0].toString();
        // 获取分隔符
        String splitKey = objects[1].toString();
        // 字符串切分
        String[] values = args.split(splitKey);
        // 循环写出每一行
        for (String value : values) {
            // 给每一列赋值
            forwardValues[0] = value;

            forward(forwardValues);
        }
    }

    @Override
    public void close() throws HiveException {

    }
}

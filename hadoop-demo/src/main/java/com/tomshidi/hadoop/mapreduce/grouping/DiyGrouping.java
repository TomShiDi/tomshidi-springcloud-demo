package com.tomshidi.hadoop.mapreduce.grouping;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 自定义分组器，只对对象中某一属性做比较
 * @author TomShiDi
 * @since 2024/2/27 21:12
 */
public class DiyGrouping extends WritableComparator {
    public DiyGrouping() {
        super(OrderBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderBean bean1 = (OrderBean) a;
        OrderBean bean2 = (OrderBean) b;
        return bean1.getOrderId().compareTo(bean2.getOrderId());
    }
}

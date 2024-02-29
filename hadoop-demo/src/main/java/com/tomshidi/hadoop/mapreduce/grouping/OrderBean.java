package com.tomshidi.hadoop.mapreduce.grouping;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author TomShiDi
 * @since 2024/2/27 20:46
 */
public class OrderBean implements WritableComparable<OrderBean> {
    private String orderId;
    private Double price;

    @Override
    public int compareTo(OrderBean o) {
        // 判断是否为同一订单下
        int result = o.getOrderId().compareTo(this.orderId);
        if (result == 0) {
            return o.getPrice().compareTo(this.price);
        }
        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId);
        out.writeDouble(price);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.orderId = in.readUTF();
        this.price = in.readDouble();
    }

    @Override
    public String toString() {
        return orderId + "\t" + price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

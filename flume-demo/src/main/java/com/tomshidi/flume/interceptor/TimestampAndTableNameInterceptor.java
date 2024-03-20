package com.tomshidi.flume.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 将数据中的表名和时间戳放到header中
 * @author TomShiDi
 * @since 2024/3/20 13:39
 */
public class TimestampAndTableNameInterceptor implements Interceptor {

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        String bodyStr = new String(event.getBody(), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSON.parseObject(bodyStr);
        String table = jsonObject.getString("table");
        long ts = jsonObject.getLong("ts") * 1000;
        Map<String, String> headers = event.getHeaders();
        headers.put("table", table);
        headers.put("timestamp", Long.toString(ts));
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{
        @Override
        public Interceptor build() {
            return new TimestampAndTableNameInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}

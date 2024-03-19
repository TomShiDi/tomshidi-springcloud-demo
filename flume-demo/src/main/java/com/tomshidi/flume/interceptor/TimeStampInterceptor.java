package com.tomshidi.flume.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 时间漂移问题处理
 * @author TomShiDi
 * @since 2024/3/19 10:29
 */
public class TimeStampInterceptor implements Interceptor {

    @Override
    public void initialize() {

    }

    /**
     * 将日志数据中的时间取出并替换event头部中的时间
     * @param event Event to be intercepted
     * @return event
     */
    @Override
    public Event intercept(Event event) {
        String bodyStr = new String(event.getBody(), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSON.parseObject(bodyStr);
        Long ts = jsonObject.getLong("ts");
        event.getHeaders().put("timestamp", ts.toString());
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

    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new TimeStampInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }

}

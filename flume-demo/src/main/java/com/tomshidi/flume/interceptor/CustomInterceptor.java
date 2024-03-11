package com.tomshidi.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.List;

/**
 * @author TomShiDi
 * @since 2024/3/11 14:30
 */
public class CustomInterceptor implements Interceptor {

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        // 取出数据
        byte[] bytes = event.getBody();
        // 判断并设置对象头属性
        if (bytes[0] >= 'a' && bytes[0] <= 'z') {
            event.getHeaders().put("type", "letter");
        }
        if (bytes[0] >= '0' && bytes[0] <= '9') {
            event.getHeaders().put("type", "number");
        }
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
            return new CustomInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}

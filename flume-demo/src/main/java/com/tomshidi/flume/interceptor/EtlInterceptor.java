package com.tomshidi.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/**
 * 数据清洗，移除不符合json格式的数据
 * @author TomShiDi
 * @since 2024/3/18 11:21
 */
public class EtlInterceptor implements Interceptor {

    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        byte[] body = event.getBody();
        String str = new String(body, StandardCharsets.UTF_8);
        event.getHeaders().put("flag", String.valueOf(JsonUtil.isJsonValidate(str)));
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            intercept(event);
            if ("false".equals(event.getHeaders().get("flag"))) {
                iterator.remove();
            }
        }
        return events;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder {
        @Override
        public Interceptor build() {
            return new EtlInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}

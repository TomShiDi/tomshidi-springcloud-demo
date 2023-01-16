package com.tomshidi.demo.listeners;

import com.tomshidi.demo.event.MyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author tangshili
 * @date 2023/1/12 17:05
 */
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("MyEvent事件监听");
    }
}

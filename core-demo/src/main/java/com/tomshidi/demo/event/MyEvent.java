package com.tomshidi.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author tomshidi
 * @date 2023/1/12 17:04
 */
public class MyEvent extends ApplicationEvent {
    public MyEvent(Object source) {
        super(source);
    }
}

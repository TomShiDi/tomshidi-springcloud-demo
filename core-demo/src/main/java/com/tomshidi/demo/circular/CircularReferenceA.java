package com.tomshidi.demo.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author tomshidi
 * @date 2022/1/23 16:12
 */
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CircularReferenceA {
    private CircularReferenceB circularReferenceB;

    @Lazy
    @Autowired
    public void setCircularReferenceB(CircularReferenceB circularReferenceB) {
        this.circularReferenceB = circularReferenceB;
    }
}

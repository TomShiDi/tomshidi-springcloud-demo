package com.tomshidi.demo.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * @author tomshidi
 * @date 2022/1/23 16:13
 */
//@ComponentInterface
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CircularReferenceB {
    private CircularReferenceA circularReferenceA;

    @Autowired
    public CircularReferenceB(CircularReferenceA circularReferenceA) {
        this.circularReferenceA = circularReferenceA;
    }
}

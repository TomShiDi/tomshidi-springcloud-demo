package com.tomshidi.demo.controller;

import com.tomshidi.demo.config.TomshidiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author tomshidi
 * @date 2023/1/29 14:35
 */
@RestController
@RequestMapping("/advice")
public class AdviceController {

    private TomshidiConfig tomshidiConfig;

    @GetMapping(path = "/normal")
    public Object normallyMethod() {
        return this.tomshidiConfig;
    }

    @GetMapping(path = "/exception")
    public Object exceptionMethod() {
        return 2 / 0;
    }

    @Autowired
    public void setTomshidiConfig(TomshidiConfig tomshidiConfig) {
        this.tomshidiConfig = tomshidiConfig;
    }
}

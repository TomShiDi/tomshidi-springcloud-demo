package com.tomshidi.demo.controller;

import com.tomshidi.base.dto.CommonResponse;
import com.tomshidi.demo.config.TomshidiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


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

    @PostMapping(value = "/body_normal", produces = {"application/json"})
    public Object normallyRequestBodyMethod(@RequestBody CommonResponse<String> commonResponse) {
        return commonResponse;
    }

    @GetMapping("/date_format")
    public Date dataFormat(@RequestParam("date") Date date) {
        return date;
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

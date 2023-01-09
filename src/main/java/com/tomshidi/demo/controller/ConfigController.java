package com.tomshidi.demo.controller;

import com.tomshidi.demo.config.NacosRefreshableConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tomshidi
 * @date 2022/12/9 13:45
 */
@RestController
@RequestMapping("/config")
public class ConfigController implements ApplicationContextAware {

    @Autowired
    private NacosRefreshableConfig nacosRefreshableConfig;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @RequestMapping("/getConfig")
    public String getConfig() {
        return nacosRefreshableConfig.getName();
    }
}

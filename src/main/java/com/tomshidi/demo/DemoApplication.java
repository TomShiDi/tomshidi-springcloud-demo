package com.tomshidi.demo;


import com.tomshidi.demo.component.ComponentInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.TimeUnit;

/**
 *
 */
@SpringBootApplication
@ServletComponentScan
public class DemoApplication implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    private ComponentInterface component;

    private static ApplicationContext applicationContext;

    @Autowired
    public void setComponent(ComponentInterface component) {
        this.component = component;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DemoApplication.class, args);
        while (true) {
            LOGGER.info("************* 日志打印 ******************");
            TimeUnit.SECONDS.sleep(2);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DemoApplication.applicationContext = applicationContext;
    }

}

package com.tomshidi.demo;


import com.tomshidi.demo.client.HttpFeignClient;
import com.tomshidi.demo.component.ComponentInterface;
import com.tomshidi.demo.config.ApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 *
 */
@SpringBootApplication(scanBasePackages = "com.tomshidi")
@ServletComponentScan
public class DemoApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    private ComponentInterface component;


    @Autowired
    public void setComponent(ComponentInterface component) {
        this.component = component;
    }

    public static void main(String[] args) throws URISyntaxException {
        SpringApplication.run(DemoApplication.class, args);
        ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
        HttpFeignClient feignClient = applicationContext.getBean(HttpFeignClient.class);
        String s = feignClient.get(new URI("https://kunpeng.csdn.net/ad/json/integrate/list?positions=932"), new HashMap<>(0));
        LOGGER.info("feign远程调用结果为：{}", s);
    }

}

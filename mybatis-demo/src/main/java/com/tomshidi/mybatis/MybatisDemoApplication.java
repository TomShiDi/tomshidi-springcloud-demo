package com.tomshidi.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tomshidi
 * @date 2023/2/9 10:49
 */
@SpringBootApplication(scanBasePackages = "com.tomshidi")
public class MybatisDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisDemoApplication.class, args);
    }
}

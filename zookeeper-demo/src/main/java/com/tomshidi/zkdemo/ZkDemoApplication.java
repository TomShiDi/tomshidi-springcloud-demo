package com.tomshidi.zkdemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;

/**
 * @author TomShiDi
 * @since 2024/1/23 13:51
 */
//@SpringBootApplication
public class ZkDemoApplication {
    public static void main(String[] args) {
//        SpringApplication.run(ZkDemoApplication.class, args);
    }
}

package com.tomshidi.zkdemo;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
/**
 * 
 * @author TomShiDi
 * @since 2024/1/23 14:33
 */
class ZkDemoApplicationTest {

    private static CuratorFramework client;

    @BeforeAll
    public static void init() {
        // 1.指定重试策略
        // 参数1为重试间隔时间，参数2为最多重试次数
        RetryPolicy exponentialBackoffRetry = new ExponentialBackoffRetry(100, 5);

        // 2.获取Zookeeper客户端对象
        String clusterUrls = "192.168.232.201:2181,192.168.232.202:2181,192.168.232.203:2181";
        client = CuratorFrameworkFactory.newClient(clusterUrls, exponentialBackoffRetry);

        // 3.启动客户端
        client.start();
    }

    /**
     * 创建节点
     */
    @Test
    public void createNode() throws Exception {
        // 创建节点
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/app/tomshidi/data2", "Hello world".getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取节点数据
     *
     * @throws Exception 异常
     */
    @Test
    public void getNodeData() throws Exception {
        byte[] bytes = client.getData()
                .forPath("/app/tomshidi/data2");
        String data = new String(bytes);
        System.out.println("节点数据为：" + data);
    }

    /**
     * 删除节点
     *
     * @throws Exception 异常
     */
    @Test
    public void delNode() throws Exception {
        client.delete()
                .deletingChildrenIfNeeded()
                .forPath("/app");
    }

    @Test
    public void watchNode() throws Exception {
        TreeCache treeCache = new TreeCache(client, "/app1");
        treeCache.getListenable().addListener((curatorFramework, treeCacheEvent) -> {
            switch (treeCacheEvent.getType()) {
                case NODE_ADDED:
                    System.out.println("监控到增加节点事件");
                    break;
                case NODE_REMOVED:
                    System.out.println("监控到移除节点事件");
                    break;
                case NODE_UPDATED:
                    System.out.println("监控到节点修改事件");
                    break;
            }
        });
        treeCache.start();

        TimeUnit.MINUTES.sleep(6);
    }

    @AfterAll
    public static void close() {
        client.close();
    }
}
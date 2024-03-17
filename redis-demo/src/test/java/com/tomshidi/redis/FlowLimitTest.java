package com.tomshidi.redis;

import com.tomshidi.redis.config.FlowLimitConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TomShiDi
 * @since 2024/3/17 17:32
 */
@SpringBootTest
class FlowLimitTest {

    @Autowired
    private FlowLimit flowLimit;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private FlowLimitConfig flowLimitConfig;

    @Test
    void tryAcquire() throws InterruptedException {
        // 将令牌数置零，模拟资源不足的场景
        redisTemplate.opsForHash().put(flowLimitConfig.getLimitKeyPrefix() + "/path/test", "tokens", String.valueOf(0));
        // 将上次刷新时间改为一秒内
        redisTemplate.opsForHash().put(flowLimitConfig.getLimitKeyPrefix() + "/path/test", "last_fill_time", String.valueOf(System.currentTimeMillis()));
//        TimeUnit.MICROSECONDS.sleep(300);
        for (int i = 0; i < 10; i++) {
            boolean result = flowLimit.tryAcquire("/path/test", 1);
            System.out.println(result ? "放行" : "拦截");
        }
    }
}
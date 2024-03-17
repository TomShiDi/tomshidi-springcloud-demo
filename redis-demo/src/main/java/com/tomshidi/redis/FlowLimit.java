package com.tomshidi.redis;

import com.tomshidi.redis.config.FlowLimitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author TomShiDi
 * @since 2024/3/17 14:56
 */
@Component
public class FlowLimit {

    private FlowLimitConfig flowLimitConfig;

    private StringRedisTemplate redisTemplate;

    public boolean tryAcquire(String resourceKey, Integer tokens) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/flow-limit.lua")));
        List<String> keyList = Collections.singletonList(flowLimitConfig.getLimitKeyPrefix() + resourceKey);
        Long result = redisTemplate.execute(redisScript,
                keyList,
                flowLimitConfig.getRate().toString(),
                flowLimitConfig.getCapacity().toString(),
                tokens.toString());
        return Long.valueOf(1).equals(result);
    }

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setFlowLimitConfig(FlowLimitConfig flowLimitConfig) {
        this.flowLimitConfig = flowLimitConfig;
    }
}

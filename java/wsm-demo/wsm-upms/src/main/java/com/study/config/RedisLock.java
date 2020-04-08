package com.study.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * redis锁
 */
@Component
public class RedisLock {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private DefaultRedisScript<Long> redisScript;

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 获取锁（单节点）
     *
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public boolean lock(String key, String value, long timeout) {
        //执行set命令
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.MILLISECONDS);
        return result != null && result;
    }

    /**
     * 释放锁（单节点）
     *
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key, String value) {
        //使用Lua脚本：先判断是否是自己设置的锁，再执行删除
        Long result = stringRedisTemplate.execute(redisScript, Arrays.asList(key, value));
        //返回最终结果
        return RELEASE_SUCCESS.equals(result);
    }

    /**
     * 配置Lua脚本
     *
     * @return
     */
    @Bean
    public DefaultRedisScript<Long> defaultRedisScript() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptText("if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end");
//        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("delete.lua")));
        return defaultRedisScript;
    }

}
package com.study.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Configuration
public class CacheConfig {

    @Bean("myKeyGenerator")
    public KeyGenerator myKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                List<SimpleKey> simpleKeys = new ArrayList<>();
                Object[] object1 = (Object[]) objects[0];
                for (Object object : object1) {
                    SimpleKey simpleKey = new SimpleKey(object);
                    simpleKeys.add(simpleKey);
                }
                return simpleKeys;
            }
        };
    }

    //    @Bean("myCacheManager")
    public CacheManager myCacheManager() {
        return new CacheManager() {
            @Override
            public Cache getCache(String s) {
                return null;
            }

            @Override
            public Collection<String> getCacheNames() {
                return null;
            }
        };
    }

    //    @Bean("myCacheResolver")
    public CacheResolver myCacheResolver() {
        return new CacheResolver() {
            @Override
            public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> cacheOperationInvocationContext) {
                return null;
            }
        };
    }

}

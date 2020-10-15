package com.beijing.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 自定义生成cache中的key值使用KeyGenerator接口
 *
 * @Author zc217
 * @Date 2020/10/15
 */
@Configuration
public class MyCacheKeyConfig {

    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + '[' + Arrays.asList(params).toString() + ']';
            }
        };
    }
}

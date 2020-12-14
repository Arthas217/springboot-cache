package com.beijing.cache.config;

import com.beijing.cache.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

/**
 * 自定义RedisTemplate序列化json
 *
 * @Author zc217
 * @Date 2020/10/15
 */
@Configuration
public class MyRedisTemplateConfig {

    // 参考RedisAutoConfiguration 配置RedisTemplate和StringRedisTemplate
    @Bean
    public RedisTemplate<Object, Employee> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 参数是接口RedisSerializer
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Employee.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }
}

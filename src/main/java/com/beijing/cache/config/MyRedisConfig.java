package com.beijing.cache.config;

import com.beijing.cache.bean.Department;
import com.beijing.cache.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.net.UnknownHostException;
import java.time.Duration;

/**
 * 自定义RedisTemplate序列化json
 *
 * @Author zc217
 * @Date 2020/10/15
 */
@Configuration
public class MyRedisConfig {

    //测试用：下面redisCacheManager这个bean，也可以。
    // 配置参考RedisAutoConfiguration 配置RedisTemplate、和StringRedisTemplate
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

    //配置参考RedisCacheConfiguration 配置RedisCacheManager
    // https://stackoverflow.com/questions/51418161/how-to-create-rediscachemanager-in-spring-data-2-0-x
    @Bean
    public RedisCacheManager employeeCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig() //获取默认的key前缀 格式：cacheName:key
//                .disableCachingNullValues() // 查询id没有数据时，会报错 Cache 'emp' does not allow 'null' values.Avoid storing null via '@Cacheable(unless="#result == null")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.
                .entryTtl(Duration.ofMinutes(2))  // 缓存1min过期时间

                //序列化区别https://blog.csdn.net/bai_bug/article/details/81222519
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
                //注意Jackson2JsonRedisSerializer参数后面的对象类型
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer(Employee.class)))

                // 自定义设置key前缀 参考https://www.cnblogs.com/lzfhope/p/12459759.html
                .computePrefixWith(cacheName -> "emp-");

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
//                .RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }

    //java.lang.IllegalStateException: No CacheResolver specified, and no unique bean of type CacheManager found.
    // Mark one as primary or declare a specific CacheManager to use.
    @Primary
    @Bean
    public RedisCacheManager deptCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(2))  // 缓存1min过期时间
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer(Department.class)))
                .computePrefixWith(cacheName -> "dept-");
        return RedisCacheManager
                .RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }

}

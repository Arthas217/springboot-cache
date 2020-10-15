package com.beijing.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 搭建基本环境
 * 1.导入sql文件创建表
 * 2.创建bean
 * 3.整合mybatis
 *   1）数据源
 *   2) 注解版mybatis(mapper接口、配置接口位置）
 * 4.使用缓存
 *   1）开启注解的缓存
 *   2）标注缓存注解  @Cacheable/@CacheEvict/@CachePut
 *
 *
 * 整合redis缓存
 *  1. 引入starter依赖，配置redis主机
 *  2. RedisAutoConfiguration自动配置生效  (RedisTemplate/StringRedisTemplate操作redis)
 *  3. 测试缓存（测试类）缓存配置类中 RedisCacheConfiguration 配置生效了，默认是SimpleCacheConfiguration此时不生效
 *          因为缓存配置类中的类是有顺序--CacheAutoConfiguration及ConditionalOnMissingBean注解条件，
 *          该缓存原理CacheManager==concurrentMapCache)
 *  4. reds缓存原理：
 *         RedisCacheManager的loadCaches来获得缓存组件
 *         RedisCacheManager
 *         默认序列化jdk，RedisTemplate<Object, Object>操作数据
 */
@MapperScan(value = "com.beijing.cache.mapper")
@SpringBootApplication
@EnableCaching
public class SpringbootCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheApplication.class, args);
    }

}

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
 */
@MapperScan(value = "com.beijing.cache.mapper")
@SpringBootApplication
@EnableCaching
public class SpringbootCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheApplication.class, args);
    }

}

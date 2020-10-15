package com.beijing.cache.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * 或者配置 mybatis.configuration.map-underscore-to-camel-case=true
 * @Author zc217
 * @Date 2020/10/15
 */
@org.springframework.context.annotation.Configuration
public class MybatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {
                //开启驼峰与_的适配
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}

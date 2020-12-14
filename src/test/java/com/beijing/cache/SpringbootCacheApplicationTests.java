package com.beijing.cache;

import com.beijing.cache.bean.Employee;
import com.beijing.cache.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.sql.DataSource;

@SpringBootTest
class SpringbootCacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    DataSource dataSource;

    @Test
    void dataSource() {
        Employee employee = employeeMapper.getEmpById(4);
        System.out.println(employee.getId());
        System.out.println(employee.getdId());
        System.out.println(dataSource.getClass());
    }

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 需要MyRedisConfig配置类中redisTemplate这个bean失效
     */
    @Test
    void testRedisOP() {
        // 简单字符串kv操纵
        ValueOperations<String, String> str = stringRedisTemplate.opsForValue();
        str.append("k1", "hello");// 操作字符串
        ListOperations<String, String> list = stringRedisTemplate.opsForList();
        list.leftPush("list", "1");// 操作列表
        list.leftPush("list", "2");

        // 操作kv对象 默认jdk序列化机制 (kv都是二进制输出）--解决参考CustomerRedisTemplateTest
        ValueOperations ops = redisTemplate.opsForValue();
        Employee employee = employeeMapper.getEmpById(1);
        ops.set("emp", employee);
    }

}

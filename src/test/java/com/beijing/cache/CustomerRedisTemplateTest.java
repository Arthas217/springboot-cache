package com.beijing.cache;

import com.beijing.cache.bean.Employee;
import com.beijing.cache.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 由MyRedisConfig类中redisTemplate 处理1）对象转json格式 2）改变RedisTemplate序列化规则
 * @Author zc217
 * @Date 2020/12/14
 */
@SpringBootTest
public class CustomerRedisTemplateTest {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RedisTemplate<Object, Employee> redisTemplate; //操作v为json格式

    @Test
    void testCustomerRedisOP(){
        ValueOperations<Object, Employee> op1 = redisTemplate.opsForValue();
        Employee employee = employeeMapper.getEmpById(1);
        op1.set("emp02", employee);
    }
}

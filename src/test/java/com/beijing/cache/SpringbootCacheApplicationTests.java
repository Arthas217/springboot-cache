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

@SpringBootTest
class SpringbootCacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    void dataSource() {
        Employee employee = employeeMapper.getEmpById(4);
        System.out.println(employee.getId());
        System.out.println(employee.getdId());
    }


    @Autowired
    RedisTemplate redisTemplate; // kv对象

    @Autowired
    RedisTemplate<Object, Employee> employeeRedisTemplate; // kv对象 json格式

    @Autowired
    StringRedisTemplate stringRedisTemplate; //字符串

    /**
     * http://doc.redisfans.com/
     * string list set zset hash
     */
    @Test
    void testRedisOP() {
//        ValueOperations<String, String> str = stringRedisTemplate.opsForValue();
//        str.append("k1", "hello");// 操作字符串
//
//        ListOperations<String, String> list = stringRedisTemplate.opsForList();
//        list.leftPush("list", "1");// 操作列表
//        list.leftPush("list", "2");
//
//        ValueOperations ops = redisTemplate.opsForValue();
//        Employee employee = employeeMapper.getEmpById(1);
//        // 操作对象 默认jdk序列化机制
//        ops.set("emp", employee);

        // 以json格式 1）对象转json  2）改变RedisTemplate序列化规则
        ValueOperations<Object, Employee> op1 = employeeRedisTemplate.opsForValue();
        Employee employee = employeeMapper.getEmpById(4);
        op1.set("emp02", employee);

    }

}

package com.beijing.cache;

import com.beijing.cache.bean.Employee;
import com.beijing.cache.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootCacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    void dataSource() {
        Employee employee = employeeMapper.getEmpById(1);
        System.out.println(employee.getId());
        System.out.println(employee.getdId());
    }

}

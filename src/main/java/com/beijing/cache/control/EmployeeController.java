package com.beijing.cache.control;

import com.beijing.cache.bean.Employee;
import com.beijing.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zc217
 * @Date 2020/10/15
 */
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    // http://localhost:8080/emp/1
    @GetMapping("/emp/{id}")
    public Employee getEmpInfo(@PathVariable("id") Integer id){
        return employeeService.getEmp(id);
    }

    // http://localhost:8080/updateemp?id=1&lastName=zuocheng&email=1&gender=1&dId=2
    @GetMapping("/updateemp")
    public Employee updateEmpInfo(Employee employee){
        return employeeService.updateEmp(employee);
    }

    // http://localhost:8080/deleteEmp/3
    @GetMapping("/deleteEmp/{id}")
    public void deleteEmpInfo(@PathVariable("id") Integer id){
        employeeService.deleteEmp(id);
    }

    // http://localhost:8080/emp/name/tiandi
    @GetMapping("/emp/name/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName){
        return employeeService.getEmpByLastName(lastName);
    }
}

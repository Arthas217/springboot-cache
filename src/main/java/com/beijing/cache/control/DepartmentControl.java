package com.beijing.cache.control;

import com.beijing.cache.bean.Department;
import com.beijing.cache.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zc217
 * @Date 2020/10/15
 */
@RestController
public class DepartmentControl {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/dept/{id}")
    public Department getDetpById(@PathVariable("id") Integer id) {
        return departmentService.getDeptById(id);
//        return departmentService.getDeptById2(id);
    }
}

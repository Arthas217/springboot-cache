package com.beijing.cache.service;

import com.beijing.cache.bean.Department;
import com.beijing.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

/**
 * @Author zc217
 * @Date 2020/10/15
 */
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Cacheable(cacheNames = "dept") //由于缓存管理器中的@Primarty, cacheManager="deptCacheManager"也可以省略
    public Department getDeptById(Integer id) {
        System.out.println("查询部门id=" + id);
        return departmentMapper.getDepById(id);
    }


    //编码方式***---使用缓存管理操作缓存的代码实现
    @Autowired
    @Qualifier("deptCacheManager")
    RedisCacheManager deptCacheManager;

    @Cacheable(value = "dept2")
    public Department getDeptById2(Integer id) {
        System.out.println("查询部门id=" + id);
        Department dept = departmentMapper.getDepById(id);
        Cache cache = deptCacheManager.getCache("dept2");
        cache.put(id, dept);
        return dept;
    }


}

package com.beijing.cache.service;

import com.beijing.cache.bean.Department;
import com.beijing.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
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


    // 注意缓存的数据能存入redis  存的是detp的json数据，但是反序列化时，由于Jackson2JsonRedisSerializer后面类型是Employee所以转化失败
    @Cacheable(value = "dept", cacheManager = "deptCacheManager")  /*cacheManager="deptCacheManager"也可以省略  由于@Primarty*/
    public Department getDeptById(Integer id) {
        System.out.println("查询部门id=" + id);
        return departmentMapper.getDepById(id);
    }


    //使用缓存管理操作缓存的代码实现
    @Qualifier("deptCacheManager")
    @Autowired
    RedisCacheManager redisCacheManager;

    @Cacheable(value = "dept2")
    public Department getDeptById2(Integer id) {
        System.out.println("查询部门id=" + id);
        Department dept = departmentMapper.getDepById(id);
        Cache cache = redisCacheManager.getCache("dept2");
        cache.put(id, dept);
        return dept;
    }


}

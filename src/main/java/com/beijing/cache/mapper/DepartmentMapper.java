package com.beijing.cache.mapper;

import com.beijing.cache.bean.Department;
import org.apache.ibatis.annotations.Select;

/**
 * @Author zc217
 * @Date 2020/10/15
 */
public interface  DepartmentMapper {

    @Select("select * from department where id=#{id}")
    Department getDepById(Integer id);
}

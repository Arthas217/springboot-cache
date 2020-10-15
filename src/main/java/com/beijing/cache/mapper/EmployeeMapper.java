package com.beijing.cache.mapper;

import com.beijing.cache.bean.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author zc217
 * @Date 2020/10/15
 */
public interface EmployeeMapper {

    @Select("select * from employee where id=#{id}")
    Employee getEmpById(Integer id);

    @Insert("insert into employee(lastName,email,gender,d_id) values " +
            "#{lastName},email=#{email},gender=#{gender},d_id=#{dId}")
    void insertEmp(Employee employee);

    @Update("update employee set lastName=#{lastName} ,email=#{email}," +
            "gender=#{gender} ,d_id=#{dId} where id=#{id}")
    void updateEmp(Employee employee);

    @Delete("delete from employee where id=#{id}")
    void deleteEmp(Integer id);

    @Select("select * from employee where lastName=#{lastName}")
    Employee getEmpByLastName(String lastName);
}

package com.beijing.cache.service;

import com.beijing.cache.bean.Employee;
import com.beijing.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * 缓存原理
 * 1. 自动配置 CacheAutoConfiguration
 * 其中导入了CacheConfigurationImportSelector，导入缓存配置类多个等。
 * 2. 默认缓存配置类哪个生效？debug=true看下：SimpleCacheConfiguration
 * 给容器中注册bean为ConcurrentMapCacheManager,核心ConcurrentMapCache，它可以lookup，put，evict获取、创建，销毁）
 *
 * @Cacheable运行流程： 1. 调用方法前先从CacheManager中查Cache组件(ConcurrentMapCache)，按照cacheNames获取，第一次取缓存如果没有就会自动创建
 * 2. 去缓存中查询内存，使用一个key，默认是方法的参数，key按照某策略生成的（SimpleKeyGenerator）
 * 如果没有参数，key = new SimpleKey
 * 如果一个参数，key = 参数值
 * 如果多个参数，key =new SimpleKey(param)
 * 3. 没有查到缓存就调用目标方法,
 * 4. 将目标方法返回的结果,放进缓存中
 * <p>
 * <p>
 * 注解中参数
 * cacheNames/values 指定缓存组件的名字，返回结果放在哪个缓存中，可以指定多个名字
 * key 如key = "#root.args[0]", #id, #a0 ；自定义key= getEmp[2]  key = "#root.methodName"+'['+'#id'+']'
 * keyGenerator自定义key  参考MyCacheKeyConfig类
 * /cacheManager/cacheResolver c缓存管理器
 * /condition 符合条件情况下有缓存  condition = "#id>1"
 * unless 指定条件为true，返回的值不会被缓存
 * sync 异步模式  时unless失效
 * @Author zc217
 * @Date 2020/10/15
 */
@Service
@CacheConfig(cacheNames = "emp")
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    //先获取key，然后执行目标方法，再放入缓存
    @Cacheable(cacheNames = {"emp"},
//            keyGenerator = "myKeyGenerator",
            condition = "#id>0",
            unless = "#id<0")
    public Employee getEmp(Integer id) {
        System.out.println("查询id=" + id + " 员工");
        Employee employee = employeeMapper.getEmpById(id);
        return employee;
    }


    //@CachePut先调用方法结果，再更新到缓存中
    // 例如： 第一次查询key=1，更新时的key=employee，虽然更新了数据到DB，然后请求时返回cache的key=1（原来的数据）
    // 原因：这里注意key的变化,
    // 解决：保证两个注解中添加，更新的key是同一个key。添加key = "#employee.id" 或者key = "#result.id"(cachealbe不能使用）
    @CachePut(cacheNames = "emp", key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("更新员工:" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    //缓存清除 key:要指定清除的数据
    // allEntries = true指定缓存中所有key都清空，这时不需要使用key了
    // beforeInvocation 默认是false  方法执行之后执行缓存清除
    @CacheEvict(cacheNames = "emp", key = "#id")
    public void deleteEmp(Integer id) {
        System.out.println("删除员工id=" + id);
        employeeMapper.deleteEmp(id);
        int i = 1 / 0;
    }



    //指定多个缓存规则
    // http://localhost:8080/emp/4 走缓存   因为key = "#result.id" 结果放入缓存了。
    // http://localhost:8080/emp/name/tiandi 走DB  因为有put注解，它必须在方法返回后执行
    @Caching(
            cacheable = {
                    @Cacheable(key = "#lastName")
            },
            put = {
                    @CachePut(key = "#result.email"),
                    @CachePut(key = "#result.id")
            })
    public Employee getEmpByLastName(String lastName) {
        System.out.println("多条件Caching--------");
        return employeeMapper.getEmpByLastName(lastName);
    }

}

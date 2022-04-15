package com.liang.regiee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Employee;
import com.liang.regiee.mapper.EmployeeMapper;
import com.liang.regiee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {

        String username = employee.getUsername();
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        Employee emp = query().eq("username", username).one();
        if (emp == null){
            return R.error("用户名不存在");
        }
        if (!password.equals(emp.getPassword())){
            return R.error("密码错误");
        }
        if (emp.getStatus()==0){
            return R.error("用户已被禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);

    }
    @Override
    public R<String> logout(HttpServletRequest request) {

        request.getSession().removeAttribute("employee");
        return R.success("退出登录~");
    }
    @Override
    public R<String> saveEmployee(HttpServletRequest request, Employee employee) {
        log.info("新增员工线程id---->{}",Thread.currentThread().getId());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));

        save(employee);
        return R.success("添加成功~~~");
    }
    @Override
    public R<Page> queryPage(Integer page, Integer pageSize, String name) {
        log.info("分页查询线程id---->{}",Thread.currentThread().getId());
        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Employee> lbq = new LambdaQueryWrapper<>();
        lbq.like(StringUtils.isNotBlank(name),Employee::getName,name);
        lbq.orderByDesc(Employee::getUpdateTime);
        page(pageInfo,lbq);
        return R.success(pageInfo);
    }

    @Override
    public R<String> toUpdate(HttpServletRequest request, Employee employee) {
        log.info("更新员工线程id---->{}",Thread.currentThread().getId());

        boolean flag = updateById(employee);
        if (flag){
            return R.success("员工信息修改成功");
        }else {
            return R.error("员工信息修改失败");
        }
    }


}

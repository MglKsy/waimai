package com.liang.regiee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Employee;
import com.liang.regiee.mapper.EmployeeMapper;
import com.liang.regiee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
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

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        save(employee);
        return R.success("添加成功~~~");
    }
}

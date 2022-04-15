package com.liang.regiee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {
    R<Employee> login(HttpServletRequest request, Employee employee);

    R<String> logout(HttpServletRequest request);

    R<String> saveEmployee(HttpServletRequest request, Employee employee);

    R<Page<Employee>> queryPage(Integer page, Integer pageSize, String name);

    R<String> toUpdate(HttpServletRequest request, Employee employee);
}

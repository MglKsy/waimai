package com.liang.regiee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Employee;
import com.liang.regiee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request,
                             @RequestBody Employee employee){
        return employeeService.login(request,employee);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        return employeeService.logout(request);
    }

    @PostMapping
    public R<String> save(HttpServletRequest request,
                          @RequestBody Employee employee){
        return employeeService.saveEmployee(request,employee);
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(@RequestParam("page") Integer page,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam(value = "name",required = false) String name){
        return employeeService.queryPage(page,pageSize,name);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,
                            @RequestBody Employee employee){
        return employeeService.toUpdate(request,employee);
    }
    @GetMapping("/{id}")
    public R<Employee> queryById(@PathVariable("id")Long id){
        return R.success(employeeService.getById(id));
    }

}

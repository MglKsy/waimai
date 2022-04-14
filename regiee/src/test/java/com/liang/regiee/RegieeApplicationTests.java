package com.liang.regiee;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Employee;
import com.liang.regiee.mapper.EmployeeMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@SpringBootTest
class RegieeApplicationTests {

    @Resource
    private EmployeeMapper employeeMapper;

    @Test
    void contextLoads() {
        String phone = "13669265113";

        for (int i = 0; i < 100; i++) {
            String username = RandomStringUtils.random(10,true,true);
            String password = DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));
            String name = RandomStringUtils.random(3,true,false);
            String idNumber = "140121200304052718";
            Employee employee = new Employee();
            employee.setUsername(username);
            employee.setPassword(password);
            employee.setName(name);
            employee.setIdNumber(idNumber);
            employee.setPhone(phone);
            employee.setCreateUser(1L);
            employee.setUpdateUser(1L);
            employeeMapper.insert(employee);
        }

    }

}

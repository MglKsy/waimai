package com.liang.regiee;

import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@ServletComponentScan
public class RegieeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegieeApplication.class, args);
    }

}

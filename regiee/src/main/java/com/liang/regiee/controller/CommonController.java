package com.liang.regiee.controller;


import com.liang.regiee.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info(file.toString());
        return null;
    }
}

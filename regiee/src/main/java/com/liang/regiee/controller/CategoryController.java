package com.liang.regiee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Category;
import com.liang.regiee.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<Category>> page(@RequestParam("page")Integer page,
                                  @RequestParam("pageSize")Integer pageSize){
        return categoryService.queryPage(page,pageSize);
    }
}

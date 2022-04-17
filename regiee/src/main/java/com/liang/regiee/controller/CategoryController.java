package com.liang.regiee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Category;
import com.liang.regiee.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
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

    @PostMapping
    public R<String> save(@RequestBody Category category){
        boolean saveFlag = categoryService.save(category);
        if (saveFlag){
            return R.success("新增分类成功");
        }else {
            return R.error("新增分类失败");
        }
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){
        boolean updateFlag = categoryService.updateById(category);
        if (updateFlag){
            return R.success("修改分类成功");
        }else {
            return R.error("修改分类失败");
        }
    }

    @DeleteMapping
    public R<String> delete(@RequestParam(value = "ids",required = false)Long id){
        log.info("要删除的分类id----->{}",id);
        log.info("缓冲");
        return categoryService.delete(id);
    }

    @GetMapping("/list")
    public R<List<Category>> list(@RequestParam("type") Integer type){
        LambdaQueryWrapper<Category> lqb = new LambdaQueryWrapper<>();
        lqb.eq(Category::getType,type);
        List<Category> list = categoryService.list(lqb);
        return R.success(list);
    }
}

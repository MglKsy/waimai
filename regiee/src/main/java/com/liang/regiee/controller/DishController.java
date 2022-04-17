package com.liang.regiee.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.regiee.common.R;
import com.liang.regiee.dto.DishDto;
import com.liang.regiee.entity.Dish;
import com.liang.regiee.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.print.attribute.standard.MediaSize;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private DishService dishService;

    @PostMapping
    public R<String> saveDish(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        return dishService.saveDishWithDishFlavor(dishDto);
    }

    @GetMapping("/page")
    public R<Page<DishDto>> page(@RequestParam("page")Integer page,
                              @RequestParam("pageSize")Integer pageSize,
                              @RequestParam(value = "name",required = false)String name){
        return dishService.queryPage(page,pageSize,name);
    }
    @GetMapping("/{id}")
    public R<DishDto> getDishDtoById(@PathVariable("id")Long id){
        return dishService.getDishDtoById(id);
    }

    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto){

        log.info(dishDto.toString());
        return dishService.updateDish(dishDto);
    }
}

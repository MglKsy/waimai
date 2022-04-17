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
import java.util.ArrayList;
import java.util.List;

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

    @DeleteMapping
    public R<String> deleteById(@RequestParam("ids") ArrayList<Long> ids){
        dishService.removeBatchByIds(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/0")
    public R<String> updateDishStatusToZero(@RequestParam("ids")ArrayList<Long> ids){

        return dishService.updateDishStatusToZero(ids);
    }

    @PostMapping("/status/1")
    public R<String> updateDishStatusToOne(@RequestParam("ids")ArrayList<Long> ids){

        return dishService.updateDishStatusToOne(ids);
    }

}

package com.liang.regiee.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.regiee.common.R;
import com.liang.regiee.dto.SetmealDto;
import com.liang.regiee.entity.Setmeal;
import com.liang.regiee.service.SetmealDishService;
import com.liang.regiee.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @PostMapping
    public R<String> savaWithDish(@RequestBody SetmealDto setmealDto){
        return setmealService.savaWithDish(setmealDto);
    }

    @GetMapping("/page")
    public R<Page<SetmealDto>> queryPage(@RequestParam("page")Integer page,
                                         @RequestParam("pageSize")Integer pageSize,
                                         @RequestParam(value = "name",required = false)String name){

        return setmealService.queryPage(page,pageSize,name);
    }

    @PostMapping("/status/0")
    public R<String> updateSetmealStatusToZero(@RequestParam("ids") ArrayList<Long> ids){

        return setmealService.updateDishStatusToZero(ids);
    }

    @PostMapping("/status/1")
    public R<String> updateSetmealStatusToOne(@RequestParam("ids")ArrayList<Long> ids){

        return setmealService.updateDishStatusToOne(ids);
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable("id")Long id){
        return setmealService.getDtoInfoById(id);
    }

    @PutMapping
    public R<String> updateSetmealWithDish(@RequestBody SetmealDto setmealDto){
        return setmealService.updateSetmealWithDish(setmealDto);
    }

    @DeleteMapping
    public R<String> removeWithDish(@RequestParam("ids")List<Long> ids){
        return setmealService.removeWithDish(ids);
    }
}

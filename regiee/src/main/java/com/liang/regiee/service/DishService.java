package com.liang.regiee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.regiee.common.R;
import com.liang.regiee.dto.DishDto;
import com.liang.regiee.entity.Dish;

import java.util.ArrayList;

public interface DishService extends IService<Dish> {
    R<String> saveDishWithDishFlavor(DishDto dishDto);

    R<Page<DishDto>> queryPage(Integer page, Integer pageSize,String name);

    R<DishDto> getDishDtoById(Long id);

    R<String> updateDish(DishDto dishDto);

    R<String> updateDishStatusToZero(ArrayList<Long> ids);

    R<String> updateDishStatusToOne(ArrayList<Long> ids);
}

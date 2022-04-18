package com.liang.regiee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.regiee.common.R;
import com.liang.regiee.dto.SetmealDto;
import com.liang.regiee.entity.Setmeal;

import java.util.ArrayList;
import java.util.List;

public interface SetmealService extends IService<Setmeal > {
    R<String> savaWithDish(SetmealDto setmealDto);

    R<Page<SetmealDto>> queryPage(Integer page, Integer pageSize, String name);

    R<String> updateDishStatusToZero(ArrayList<Long> ids);

    R<String> updateDishStatusToOne(ArrayList<Long> ids);

    R<SetmealDto> getDtoInfoById(Long id);

    R<String> updateSetmealWithDish(SetmealDto setmealDto);

    R<String> removeWithDish(List<Long> ids);
}

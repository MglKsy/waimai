package com.liang.regiee.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.regiee.common.CustomException;
import com.liang.regiee.common.R;
import com.liang.regiee.dto.SetmealDto;
import com.liang.regiee.entity.Category;
import com.liang.regiee.entity.Setmeal;
import com.liang.regiee.entity.SetmealDish;
import com.liang.regiee.mapper.CategoryMapper;
import com.liang.regiee.mapper.SetmealDishMapper;
import com.liang.regiee.mapper.SetmealMapper;

import com.liang.regiee.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Resource
    private SetmealDishMapper setmealDishMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SetmealMapper setmealMapper;

    @Override
    @Transactional
    public R<String> savaWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        for (SetmealDish setmealDish : setmealDishes) {
            setmealDishMapper.insert(setmealDish);
        }

        return R.success("成功添加");
    }

    @Override
    public R<Page<SetmealDto>> queryPage(Integer page, Integer pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null,Setmeal::getName,name).orderByDesc(Setmeal::getUpdateTime);
        this.page(setmealPage,wrapper);

        BeanUtils.copyProperties(setmealPage,setmealDtoPage);

        List<Setmeal> setmealList = setmealPage.getRecords();
        List<SetmealDto> setmealDtoList = setmealList.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);

            Category category = categoryMapper.selectById(item.getCategoryId());
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(setmealDtoList);

        return R.success(setmealDtoPage);

    }

    @Override
    public R<String> updateDishStatusToZero(ArrayList<Long> ids) {
        for (Long id : ids) {
            setmealMapper.updateToZero(id);
        }
        return R.success("成功");
    }

    @Override
    public R<String> updateDishStatusToOne(ArrayList<Long> ids) {
        for (Long id : ids) {
            setmealMapper.updateToOne(id);
        }
        return R.success("失败");
    }

    @Override
    public R<SetmealDto> getDtoInfoById(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> wr = new LambdaQueryWrapper<>();
        wr.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishes = setmealDishMapper.selectList(wr);

        setmealDto.setSetmealDishes(setmealDishes);
        return R.success(setmealDto);
    }

    @Override
    @Transactional
    public R<String> updateSetmealWithDish(SetmealDto setmealDto) {
        setmealMapper.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishMapper.delete(wrapper);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDishMapper.insert(setmealDish);
        }
        return R.success("修改成功");
    }

    @Override
    @Transactional
    public R<String> removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        long count = this.count(queryWrapper);
        if (count > 0){
            throw new CustomException("套餐正在售卖中，无法删除");
        }
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishMapper.deleteBatchIds(ids);
        return R.success("删除套餐成功");
    }
}

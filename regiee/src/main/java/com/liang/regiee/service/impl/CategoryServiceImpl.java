package com.liang.regiee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.regiee.common.CustomException;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Category;
import com.liang.regiee.entity.Dish;
import com.liang.regiee.entity.Setmeal;
import com.liang.regiee.mapper.CategoryMapper;
import com.liang.regiee.service.CategoryService;
import com.liang.regiee.service.DishService;
import com.liang.regiee.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private DishService dishService;
    @Resource
    private SetmealService setmealService;
    @Override
    public R<Page<Category>> queryPage(Integer page, Integer pageSize) {
        LambdaQueryWrapper<Category> lbq = new LambdaQueryWrapper<>();
        lbq.orderByAsc(Category::getSort);
        Page<Category> categoryPage = new Page<>(page, pageSize);
        page(categoryPage,lbq);
        return R.success(categoryPage);
    }

    @Override
    public R<String> delete(Long id) {
        LambdaQueryWrapper<Dish> dishW = new LambdaQueryWrapper<>();
        dishW.eq(Dish::getCategoryId,id);
        if (dishService.count(dishW) > 0) {
            throw new CustomException("当前分类下关联了菜品，无法删除");
        }
        LambdaQueryWrapper<Setmeal> dishS = new LambdaQueryWrapper<>();
        dishS.eq(Setmeal::getCategoryId,id);
        if (setmealService.count(dishS) > 0) {
            throw new CustomException("当前分类下关联了套餐，无法删除");
        }
        removeById(id);
        return R.success("删除分类成功~！~！");
    }
}

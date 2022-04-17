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
import com.liang.regiee.mapper.DishMapper;
import com.liang.regiee.mapper.SetmealMapper;
import com.liang.regiee.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private DishMapper dishMapper;
    @Resource
    private SetmealMapper setmealMapper;
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
        if (dishMapper.selectCount(dishW) > 0) {
            throw new CustomException("当前分类下关联了菜品，无法删除");
        }
        LambdaQueryWrapper<Setmeal> dishS = new LambdaQueryWrapper<>();
        dishS.eq(Setmeal::getCategoryId,id);
        if (setmealMapper.selectCount(dishS) > 0) {
            throw new CustomException("当前分类下关联了套餐，无法删除");
        }
        removeById(id);
        return R.success("删除分类成功~！~！");
    }

    @Override
    public R<List<Category>> listCategory(Category category) {
        LambdaQueryWrapper<Category> lqb = new LambdaQueryWrapper<>();
        lqb.eq(category.getType() != null,Category::getType,category.getType());
        lqb.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = list(lqb);
        return R.success(list);
    }
}

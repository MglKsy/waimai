package com.liang.regiee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.regiee.common.R;
import com.liang.regiee.dto.DishDto;
import com.liang.regiee.entity.Category;
import com.liang.regiee.entity.Dish;
import com.liang.regiee.entity.DishFlavor;
import com.liang.regiee.mapper.CategoryMapper;
import com.liang.regiee.mapper.DishFlavorMapper;
import com.liang.regiee.mapper.DishMapper;
import com.liang.regiee.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Resource
    private DishFlavorMapper dishFlavorMapper;

    @Resource
    private CategoryMapper categoryMapper;
    @Override
    @Transactional
    public R<String> saveDishWithDishFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long id = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        for (DishFlavor flavor : flavors) {
            dishFlavorMapper.insert(flavor);
        }

        return R.success("成功%……&*");
    }

    @Override
    public R<Page<DishDto>> queryPage(Integer page, Integer pageSize,String name) {
        Page<Dish> dishPage = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> lqb = new LambdaQueryWrapper<>();
        lqb.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        lqb.like(name != null,Dish::getName,name);
        //分页查询
        this.page(dishPage,lqb);
        //属性拷贝
        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");

        List<Dish> dishList = dishPage.getRecords();
        List<DishDto> dishDtoList = dishList.stream().map((item)-> {

            Long categoryId = item.getCategoryId();
            Category category = categoryMapper.selectById(categoryId);

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dishDtoList);
        return R.success(dishDtoPage);
    }

    @Override
    public R<DishDto> getDishDtoById(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = this.getById(id);


        Long categoryId = dish.getCategoryId();

        BeanUtils.copyProperties(dish,dishDto);

        LambdaQueryWrapper<DishFlavor> lbqF = new LambdaQueryWrapper<>();
        lbqF.eq(DishFlavor::getDishId,id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(lbqF);
        dishDto.setFlavors(dishFlavors);

        Category category = categoryMapper.selectById(categoryId);
        dishDto.setCategoryName(category.getName());

        return R.success(dishDto);
    }

    @Override
    @Transactional
    public R<String> updateDish(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应的口味数据
        LambdaQueryWrapper<DishFlavor> lqb = new LambdaQueryWrapper<>();
        lqb.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorMapper.delete(lqb);

        //添加新的口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        for (DishFlavor flavor : flavors) {
            dishFlavorMapper.insert(flavor);
        }

        return R.success("修改信息成功");
    }
}

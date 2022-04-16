package com.liang.regiee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.regiee.entity.Dish;
import com.liang.regiee.mapper.DishMapper;
import com.liang.regiee.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

}

package com.liang.regiee.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.regiee.entity.DishFlavor;
import com.liang.regiee.mapper.DishFlavorMapper;
import com.liang.regiee.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}

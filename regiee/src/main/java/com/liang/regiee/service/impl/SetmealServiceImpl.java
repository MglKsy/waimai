package com.liang.regiee.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.regiee.entity.Setmeal;
import com.liang.regiee.mapper.SetmealMapper;

import com.liang.regiee.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}

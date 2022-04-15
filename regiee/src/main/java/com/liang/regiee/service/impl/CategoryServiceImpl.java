package com.liang.regiee.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Category;
import com.liang.regiee.mapper.CategoryMapper;
import com.liang.regiee.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public R<Page<Category>> queryPage(Integer page, Integer pageSize) {
        Page<Category> pageInfo = new Page<>(page,pageSize);
        page(pageInfo);
        return R.success(pageInfo);
    }
}

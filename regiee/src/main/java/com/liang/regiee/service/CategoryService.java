package com.liang.regiee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Category;

public interface CategoryService extends IService<Category> {
    R<Page<Category>> queryPage(Integer page, Integer pageSize);
}

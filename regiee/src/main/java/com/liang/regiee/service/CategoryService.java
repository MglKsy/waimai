package com.liang.regiee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.regiee.common.R;
import com.liang.regiee.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    R<Page<Category>> queryPage(Integer page, Integer pageSize);

    R<String> delete(Long id);

    R<List<Category>> listCategory(Category category);
}

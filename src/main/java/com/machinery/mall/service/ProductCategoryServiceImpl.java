package com.machinery.mall.service;

import com.machinery.mall.entity.ProductCategory;
import com.machinery.mall.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/26  15:00
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private ProductCategoryMapper categoryMapper;
    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
    @Override
    public List<ProductCategory> getAllCategories() {
        return categoryMapper.selectAll();
    }
}

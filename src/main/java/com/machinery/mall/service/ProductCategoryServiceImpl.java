package com.machinery.mall.service;

import com.machinery.mall.entity.ProductCategory;
import com.machinery.mall.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

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

    public List<ProductCategory> getCategoriesByParentId(Integer parentId) {
        return categoryMapper.selectByParentId(parentId);
    }

    public List<ProductCategory> getCategoryTree() {
        List<ProductCategory> all = categoryMapper.selectAll();
        return buildTreeWithChildren(all, 0);
    }

    private List<ProductCategory> buildTreeWithChildren(List<ProductCategory> all, Integer parentId) {
        List<ProductCategory> tree = new ArrayList<>();
        for (ProductCategory cat : all) {
            if (cat.getParentId() != null && cat.getParentId().equals(parentId)) {
                List<ProductCategory> children = buildTreeWithChildren(all, cat.getId());
                try {
                    java.lang.reflect.Method setChildren = cat.getClass().getMethod("setChildren", List.class);
                    setChildren.invoke(cat, children);
                } catch (Exception e) {
                    // 忽略异常
                }
                tree.add(cat);
            }
        }
        return tree;
    }
}

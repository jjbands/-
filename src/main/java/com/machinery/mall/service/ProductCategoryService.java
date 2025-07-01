package com.machinery.mall.service;

import com.machinery.mall.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();
    List<ProductCategory> getParentOptions();
    List<ProductCategory> getChildrenByParentId(Integer parentId);
    List<ProductCategory> getCategoriesByLevel(Integer level);
    ProductCategory getCategoryById(Integer id);
    int addCategory(ProductCategory category);
    int updateCategory(ProductCategory category);
    int deleteCategory(Integer id);
    int countByParentId(Integer parentId);
}
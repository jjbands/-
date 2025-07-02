package com.machinery.mall.service;

import com.machinery.mall.entity.ProductCategory;

import java.util.List;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/26  14:58
 */
public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();
    List<ProductCategory> getCategoriesByParentId(Integer parentId);
    List<ProductCategory> getCategoryTree();
    int addCategory(ProductCategory category);
    int updateCategory(ProductCategory category);
    int deleteCategory(Integer id);
}

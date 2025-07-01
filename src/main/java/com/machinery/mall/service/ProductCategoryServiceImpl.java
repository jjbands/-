package com.machinery.mall.service;

import com.machinery.mall.entity.ProductCategory;
import com.machinery.mall.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<ProductCategory> getParentOptions() {
        return categoryMapper.selectParentOptions();
    }

    @Override
    public List<ProductCategory> getChildrenByParentId(Integer parentId) {
        return categoryMapper.selectByParentId(parentId);
    }

    @Override
    public List<ProductCategory> getCategoriesByLevel(Integer level) {
        return categoryMapper.selectByLevel(level);
    }

    @Override
    public ProductCategory getCategoryById(Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public int addCategory(ProductCategory category) {
        // 确保 parentId 不为 null
        if (category.getParentId() == null) {
            category.setParentId(0);
        }
        return categoryMapper.insert(category);
    }

    @Override
    public int updateCategory(ProductCategory category) {
        // 确保 parentId 不为 null
        if (category.getParentId() == null) {
            category.setParentId(0);
        }
        return categoryMapper.update(category);
    }

    @Override
    public int deleteCategory(Integer id) {
        return categoryMapper.delete(id);
    }

    @Override
    public int countByParentId(Integer parentId) {
        return categoryMapper.countByParentId(parentId);
    }
}
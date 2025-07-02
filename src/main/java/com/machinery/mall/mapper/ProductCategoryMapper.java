package com.machinery.mall.mapper;

import com.machinery.mall.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/26  14:56
 */
@Mapper
public interface ProductCategoryMapper {
    List<ProductCategory> selectAll();
    List<Integer> selectAllSubCategoryIds(Integer parentId);
    List<ProductCategory> selectByParentId(Integer parentId);
    int insertCategory(ProductCategory category);
    int updateCategory(ProductCategory category);
    int deleteCategory(Integer id);
}
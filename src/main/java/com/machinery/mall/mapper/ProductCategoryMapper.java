package com.machinery.mall.mapper;

import com.machinery.mall.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryMapper {
    List<ProductCategory> selectAll();
    List<ProductCategory> selectParentOptions();
    List<ProductCategory> selectByParentId(Integer parentId);
    List<ProductCategory> selectByLevel(Integer level);
    ProductCategory selectById(Integer id);
    int insert(ProductCategory category);
    int update(ProductCategory category);
    int delete(Integer id);
    int countByParentId(Integer parentId);
}
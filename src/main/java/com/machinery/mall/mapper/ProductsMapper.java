package com.machinery.mall.mapper;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/26  14:57
 */
import com.machinery.mall.entity.Products;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductsMapper {
    List<Products> selectByCategoryId(Integer categoryId);
    List<Products> selectByName(String name);
    Products selectById(Integer id);
    List<Products> getAllProducts();
    int updateProduct(Products product);
    int deleteProduct(@Param("id") int id);
    List<Products> searchProductsByName(@Param("name") String name);
    List<Products> selectByCategoryIds(List<Integer> categoryIds);
    int updateById(Products product);
    int addProduct(Products product);
}
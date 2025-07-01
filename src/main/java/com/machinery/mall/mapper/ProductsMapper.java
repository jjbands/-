package com.machinery.mall.mapper;


import com.machinery.mall.entity.Products;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductsMapper {
    List<Products> selectByCategoryId(Integer categoryId);
    List<Products> selectByName(String name);
    Products selectById(Integer id);
    int countByCategoryId(Integer categoryId);
    List<Products> getAllProducts();
    int updateProduct(Products product);
    int deleteProduct(@Param("id") int id);
    List<Products> searchProductsByName(@Param("name") String name);
    int addProduct(Products product);
}
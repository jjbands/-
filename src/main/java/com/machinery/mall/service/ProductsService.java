package com.machinery.mall.service;

import com.machinery.mall.entity.Products;

import java.util.List;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/26  14:58
 */
public interface ProductsService {
    List<Products> getProductsByCategory(Integer categoryId);
    List<Products> getProductsByName(String name);
    Products getProductById(Integer id) ;
    List<Products> getAllProducts();
    int updateProduct(Products product);
    int deleteProduct(int id);
    List<Products> searchProductsByName(String name);
    int addProduct(Products product);
}

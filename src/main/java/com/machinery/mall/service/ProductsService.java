package com.machinery.mall.service;

import com.machinery.mall.entity.Products;

import java.util.List;


public interface ProductsService {
    List<Products> getProductsByCategory(Integer categoryId);
    List<Products> getProductsByName(String name);
    Products getProductById(Integer id) ;
    List<Products> getAllProducts();
    int updateProduct(Products product);
    int deleteProduct(int id);
    List<Products> searchProductsByName(String name);

    int addProduct(Products product);
    int countByCategoryId(Integer categoryId);

}

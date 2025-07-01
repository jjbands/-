package com.machinery.mall.service;

import com.machinery.mall.entity.Products;
import com.machinery.mall.mapper.ProductCategoryMapper;
import com.machinery.mall.mapper.ProductsMapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.machinery.mall.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.machinery.mall.mapper.ProductCategoryMapper;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductsServiceImpl implements ProductsService {


//保留但要改名
//     @Override
//     public List<Products> getProductsByCategory(Integer categoryId) {
//         return productsMapper.selectByCategoryId(categoryId);
//     }

    private ProductsMapper productsMapper;

    private ProductCategoryMapper categoryMapper;
    public ProductsServiceImpl(ProductsMapper productsMapper, ProductCategoryMapper categoryMapper) {
        this.productsMapper = productsMapper;
        this.categoryMapper = categoryMapper;
    }
    @Override
    public List<Products> getProductsByCategory(Integer categoryId) {
        // 递归查找所有子分类ID
        List<Integer> categoryIds = categoryMapper.selectAllSubCategoryIds(categoryId);
        return productsMapper.selectByCategoryIds(categoryIds);
    }


    @Override
    public List<Products> getProductsByName(String name) {
        return productsMapper.selectByName(name);
    }
    @Override
    public Products getProductById(Integer id) {
        return productsMapper.selectById(id);
    }
    @Override

    public int countByCategoryId(Integer categoryId) {
        return productsMapper.countByCategoryId(categoryId);
    }
    @Override

    public List<Products> getAllProducts() {
        return productsMapper.getAllProducts();
    }

    @Override
    public int updateProduct(Products product) {
        return productsMapper.updateProduct(product);
    }

    @Override
    public int deleteProduct(int id) {
        return productsMapper.deleteProduct(id);
    }

    @Override
    public List<Products> searchProductsByName(String name) {
        return productsMapper.searchProductsByName(name);

    }

    @Override
    public int addProduct(Products product) {
        return productsMapper.addProduct(product);
    }

}

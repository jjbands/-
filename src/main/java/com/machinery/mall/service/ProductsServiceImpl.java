package com.machinery.mall.service;

import com.machinery.mall.entity.Products;
import com.machinery.mall.mapper.ProductsMapper;
import com.machinery.mall.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/26  14:59
 */
@Service
public class ProductsServiceImpl implements ProductsService {
    private ProductsMapper productsMapper;
    public ProductsServiceImpl(ProductsMapper productsMapper) {
        this.productsMapper = productsMapper;}
    @Override
        public List<Products> getProductsByCategory(Integer categoryId) {
            return productsMapper.selectByCategoryId(categoryId);
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
    }

package com.machinery.mall.service;

import com.machinery.mall.entity.Products;
import com.machinery.mall.mapper.ProductsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductsMapper productsMapper;

    @Autowired
    public ProductsServiceImpl(ProductsMapper productsMapper) {
        this.productsMapper = productsMapper;
    }
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

package com.machinery.mall.controller;


import com.machinery.mall.entity.Products;
import com.machinery.mall.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class ProductsController {
    private ProductsService productsService;
    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;

    }
    @GetMapping("/api/products/{id}")
    public Products getProductById(@PathVariable Integer id) {
        return productsService.getProductById(id);
    }
}
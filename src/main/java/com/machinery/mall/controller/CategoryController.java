package com.machinery.mall.controller;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/26  14:54
 */
import com.machinery.mall.entity.ProductCategory;
import com.machinery.mall.entity.Products;
import com.machinery.mall.service.ProductCategoryService;
import com.machinery.mall.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CategoryController {

    private ProductCategoryService categoryService;
    private ProductsService productsService;
    @Autowired
    public CategoryController(ProductCategoryService categoryService, ProductsService productsService) {
        this.categoryService = categoryService;
        this.productsService = productsService;
    }
    @GetMapping("/categories")
    public List<ProductCategory> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/products")
    public List<Products> getProductsByCategory(@RequestParam("categoryId") Integer categoryId) {
        return productsService.getProductsByCategory(categoryId);
    }
    @GetMapping("/products/searchByCategory")
    public List<Products> searchProductsByName(@RequestParam String name) {
        return productsService.getProductsByName(name);
    }

    @GetMapping("/categories/parent/{parentId}")
    public List<ProductCategory> getCategoriesByParentId(@PathVariable Integer parentId) {
        return categoryService.getCategoriesByParentId(parentId);
    }

    @GetMapping("/categories/tree")
    public List<ProductCategory> getCategoryTree() {
        return categoryService.getCategoryTree();
    }
}
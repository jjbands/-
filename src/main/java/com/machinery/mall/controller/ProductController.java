package com.machinery.mall.controller;

import com.machinery.mall.entity.Products;
import com.machinery.mall.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    private final ProductsService productsService;

    @Autowired
    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/products/all")
    public Map<String, Object> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Products> products = productsService.getAllProducts();
            response.put("status", 0);
            response.put("data", products);
            response.put("msg", "获取成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "获取失败: " + e.getMessage());
        }
        return response;
    }
    @PostMapping("/product/add")
    public Map<String, Object> addProduct(@RequestBody Products product) {
        Map<String, Object> response = new HashMap<>();
        try {
            product.setPartsId(product.getProductId());

            int result = productsService.addProduct(product);
            if (result > 0) {
                response.put("status", 0);
                response.put("msg", "添加成功");
            } else {
                response.put("status", 1);
                response.put("msg", "添加失败");
            }
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "添加失败: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/product")
    public Map<String, Object> updateProduct(@RequestBody Products product) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = productsService.updateProduct(product);
            response.put("status", result > 0 ? 0 : 1);
            response.put("msg", result > 0 ? "更新成功" : "更新失败");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "更新失败: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/products/search")
    public Map<String, Object> searchProducts(@RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Products> products = productsService.searchProductsByName(name);
            response.put("status", 0);
            response.put("data", products);
            response.put("msg", "搜索成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "搜索失败: " + e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/product/{id}")
    public Map<String, Object> deleteProduct(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = productsService.deleteProduct(id);
            response.put("status", result > 0 ? 0 : 1);
            response.put("msg", result > 0 ? "删除成功" : "删除失败");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "删除失败: " + e.getMessage());
        }
        return response;
    }
}
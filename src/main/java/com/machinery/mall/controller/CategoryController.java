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

    @PostMapping("/categories")
    public Result addCategory(@RequestBody ProductCategory category) {
        int n = categoryService.addCategory(category);
        return n > 0 ? new Result(0, "success") : new Result(1, "fail");
    }

    @PutMapping("/categories")
    public Result updateCategory(@RequestBody ProductCategory category) {
        int n = categoryService.updateCategory(category);
        return n > 0 ? new Result(0, "success") : new Result(1, "fail");
    }

    @DeleteMapping("/categories/{id}")
    public Result deleteCategory(@PathVariable Integer id) {
        int n = categoryService.deleteCategory(id);
        return n > 0 ? new Result(0, "success") : new Result(1, "fail");
    }
}

class Result {
    private int status;
    private String msg;
    private Object data;
    public Result() {}
    public Result(int status, String msg) { this.status = status; this.msg = msg; }
    public Result(int status, String msg, Object data) { this.status = status; this.msg = msg; this.data = data; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
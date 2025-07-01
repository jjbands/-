package com.machinery.mall.controller;

import com.machinery.mall.entity.ProductCategory;
import com.machinery.mall.entity.Products;
import com.machinery.mall.service.ProductCategoryService;
import com.machinery.mall.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CategoryController {

    private final ProductCategoryService categoryService;
    private final ProductsService productsService;

    @Autowired
    public CategoryController(ProductCategoryService categoryService, ProductsService productsService) {
        this.categoryService = categoryService;
        this.productsService = productsService;
    }

    // 获取所有分类（树形结构）
    @GetMapping("/categories")
    public Map<String, Object> getCategories() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProductCategory> categories = categoryService.getAllCategories();
            response.put("status", 0);
            response.put("data", categories);
            response.put("msg", "获取成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "获取失败: " + e.getMessage());
        }
        return response;
    }

    // 获取分类树
    @GetMapping("/categories/tree")
    public Map<String, Object> getCategoryTree() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProductCategory> categories = categoryService.getAllCategories();
            response.put("status", 0);
            response.put("data", categories);
            response.put("msg", "获取成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "获取失败: " + e.getMessage());
        }
        return response;
    }

    // 获取可作为父分类的选项
    @GetMapping("/categories/parent-options")
    public Map<String, Object> getParentOptions() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProductCategory> options = categoryService.getParentOptions();
            response.put("status", 0);
            response.put("data", options);
            response.put("msg", "获取成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "获取失败: " + e.getMessage());
        }
        return response;
    }

    // 获取指定父分类的子分类
    @GetMapping("/categories/children/{parentId}")
    public Map<String, Object> getChildren(@PathVariable Integer parentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProductCategory> children = categoryService.getChildrenByParentId(parentId);
            response.put("status", 0);
            response.put("data", children);
            response.put("msg", "获取成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "获取失败: " + e.getMessage());
        }
        return response;
    }

    // 获取指定级别的分类
    @GetMapping("/categories/level/{level}")
    public Map<String, Object> getCategoriesByLevel(@PathVariable Integer level) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProductCategory> categories = categoryService.getCategoriesByLevel(level);
            response.put("status", 0);
            response.put("data", categories);
            response.put("msg", "获取成功");
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "获取失败: " + e.getMessage());
        }
        return response;
    }

    // 获取单个分类详情
    @GetMapping("/categories/{id}")
    public Map<String, Object> getCategoryById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            ProductCategory category = categoryService.getCategoryById(id);
            if (category == null) {
                response.put("status", 1);
                response.put("msg", "分类不存在");
            } else {
                response.put("status", 0);
                response.put("data", category);
                response.put("msg", "获取成功");
            }
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "获取失败: " + e.getMessage());
        }
        return response;
    }

    // 新增分类
    // 新增分类
    @PostMapping("/categories/add")
    public Map<String, Object> addCategory(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 1. 打印原始请求数据
            System.out.println("原始请求数据: " + requestMap);

            // 2. 手动映射字段到ProductCategory对象
            ProductCategory category = new ProductCategory();

            // 映射id字段 - 添加空字符串处理
            if (requestMap.containsKey("id")) {
                Object idObj = requestMap.get("id");
                if (idObj != null && !idObj.toString().trim().isEmpty()) {
                    try {
                        category.setId(Integer.valueOf(idObj.toString()));
                    } catch (NumberFormatException e) {
                        // 如果id不是有效数字，设置为null让数据库自增
                        category.setId(null);
                    }
                } else {
                    // id为空字符串或null，设置为null让数据库自增
                    category.setId(null);
                }
            }

            // 映射name字段
            if (requestMap.containsKey("name")) {
                category.setName(requestMap.get("name").toString());
            } else {
                response.put("status", 1);
                response.put("msg", "分类名称不能为空");
                return response;
            }

            // 3. 特别处理parent_id字段
            Object parentIdObj = requestMap.get("parent_id");
            System.out.println("接收到的parent_id原始值: " + parentIdObj + " 类型: " +
                    (parentIdObj != null ? parentIdObj.getClass() : "null"));

            if (parentIdObj != null) {
                try {
                    int parentId = Integer.parseInt(parentIdObj.toString());
                    category.setParentId(parentId);
                    System.out.println("成功设置parentId: " + parentId);
                } catch (NumberFormatException e) {
                    System.out.println("parentId格式错误，使用默认值0");
                    category.setParentId(0);
                }
            } else {
                System.out.println("parentId为null，使用默认值0");
                category.setParentId(0);
            }

            // 4. 验证父分类是否存在
            if (category.getParentId() != 0) {
                ProductCategory parent = categoryService.getCategoryById(category.getParentId());
                if (parent == null) {
                    System.out.println("父分类不存在，ID: " + category.getParentId());
                    response.put("status", 1);
                    response.put("msg", "父分类不存在");
                    return response;
                }
                category.setLevel(parent.getLevel() + 1);
                System.out.println("设置分类level为: " + category.getLevel());
            } else {
                category.setLevel(0);
                System.out.println("这是一级分类，level设置为0");
            }

            // 5. 设置其他字段默认值
            if (requestMap.containsKey("sort_order")) {
                try {
                    category.setSortOrder(Integer.parseInt(requestMap.get("sort_order").toString()));
                } catch (NumberFormatException e) {
                    category.setSortOrder(0);
                }
            } else {
                category.setSortOrder(0);
            }

            if (requestMap.containsKey("status")) {
                try {
                    category.setStatus(Integer.parseInt(requestMap.get("status").toString()));
                } catch (NumberFormatException e) {
                    category.setStatus(1);
                }
            } else {
                category.setStatus(1);
            }

            // 6. 打印最终对象
            System.out.println("最终分类对象: " + category);
            System.out.println("parentId最终值: " + category.getParentId());
            System.out.println("level值: " + category.getLevel());
            System.out.println("sortOrder值: " + category.getSortOrder());
            System.out.println("status值: " + category.getStatus());

            // 7. 调用Service层保存分类
            int result = categoryService.addCategory(category);
            if (result > 0) {
                System.out.println("分类添加成功，ID: " + category.getId());
                response.put("status", 0);
                response.put("msg", "添加成功");
                response.put("data", category); // 可选：返回创建的分类数据
            } else {
                System.out.println("分类添加失败");
                response.put("status", 1);
                response.put("msg", "添加失败");
            }

        } catch (Exception e) {
            System.err.println("添加分类时发生异常: " + e.getMessage());
            e.printStackTrace();
            response.put("status", 1);
            response.put("msg", "添加失败: " + e.getMessage());
        }

        return response;
    }

    // 更新分类
    @PutMapping("/categories/update")
    public Map<String, Object> updateCategory(@RequestBody ProductCategory category) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 检查分类是否存在
            ProductCategory existing = categoryService.getCategoryById(category.getId());
            if (existing == null) {
                response.put("status", 1);
                response.put("msg", "分类不存在");
                return response;
            }

            // 验证父分类
            if (category.getParentId() != existing.getParentId()) {
                if (category.getParentId() != 0) {
                    ProductCategory parent = categoryService.getCategoryById(category.getParentId());
                    if (parent == null) {
                        response.put("status", 1);
                        response.put("msg", "父分类不存在");
                        return response;
                    }
                    category.setLevel(parent.getLevel() + 1);
                } else {
                    category.setLevel(0);
                }

                // 检查是否将父分类设置为自己的子分类
                if (isCircularReference(category.getId(), category.getParentId())) {
                    response.put("status", 1);
                    response.put("msg", "不能将分类设置为自己的子分类");
                    return response;
                }
            }

            int result = categoryService.updateCategory(category);
            if (result > 0) {
                response.put("status", 0);
                response.put("msg", "更新成功");
            } else {
                response.put("status", 1);
                response.put("msg", "更新失败");
            }
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "更新失败: " + e.getMessage());
        }
        return response;
    }

    // 删除分类
    @DeleteMapping("/categories/{id}")
    public Map<String, Object> deleteCategory(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 检查是否有子分类
            int childCount = categoryService.countByParentId(id);
            if (childCount > 0) {
                response.put("status", 1);
                response.put("msg", "请先删除子分类");
                return response;
            }

            // 检查是否有商品使用此分类
            int productCount = productsService.countByCategoryId(id);
            if (productCount > 0) {
                response.put("status", 1);
                response.put("msg", "该分类下有商品，不能删除");
                return response;
            }

            int result = categoryService.deleteCategory(id);
            if (result > 0) {
                response.put("status", 0);
                response.put("msg", "删除成功");
            } else {
                response.put("status", 1);
                response.put("msg", "删除失败");
            }
        } catch (Exception e) {
            response.put("status", 1);
            response.put("msg", "删除失败: " + e.getMessage());
        }
        return response;
    }

    // 检查循环引用
    private boolean isCircularReference(Integer categoryId, Integer parentId) {
        if (parentId == 0) return false;

        // 递归检查父分类是否引用当前分类
        while (parentId != 0) {
            if (parentId.equals(categoryId)) {
                return true;
            }
            ProductCategory parent = categoryService.getCategoryById(parentId);
            if (parent == null) break;
            parentId = parent.getParentId();
        }
        return false;
    }

    // 根据分类ID获取商品
//     @GetMapping("/products")
// <<<<<<< master
//     public Map<String, Object> getProductsByCategory(@RequestParam Integer categoryId) {
//         Map<String, Object> response = new HashMap<>();
//         try {
//             List<Products> products = productsService.getProductsByCategory(categoryId);
//             response.put("status", 0);
//             response.put("data", products);
//             response.put("msg", "获取成功");
//         } catch (Exception e) {
//             response.put("status", 1);
//             response.put("msg", "获取失败: " + e.getMessage());
//         }
//         return response;
//     }

//     // 根据名称搜索商品
//     @GetMapping("/products/searchByCategory")
//     public Map<String, Object> searchProductsByName(@RequestParam String name) {
//         Map<String, Object> response = new HashMap<>();
//         try {
//             List<Products> products = productsService.getProductsByName(name);
//             response.put("status", 0);
//             response.put("data", products);
//             response.put("msg", "获取成功");
//         } catch (Exception e) {
//             response.put("status", 1);
//             response.put("msg", "获取失败: " + e.getMessage());
//         }
//         return response;
// =======
    public List<Products> getProductsByCategory(@RequestParam("categoryId") Integer categoryId) {
        return productsService.getProductsByCategory(categoryId);
    }
    @GetMapping("/products/searchByCategory")
    public List<Products> searchProductsByName(@RequestParam String name) {
        return productsService.getProductsByName(name);

    }
}
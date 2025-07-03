package com.machinery.mall.controller;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/27  10:38
 */
import com.machinery.mall.entity.ShoppingCart;
import com.machinery.mall.service.ShoppingCartService;
import com.machinery.mall.service.ProductsService;
import com.machinery.mall.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class ShoppingCartController {


    private ShoppingCartService shoppingCartService;
    private ProductsService productsService;
    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductsService productsService) {
        this.shoppingCartService = shoppingCartService;
        this.productsService = productsService;
    }

    @PostMapping
    public Map<String, Object> addToCart(@RequestBody ShoppingCart shoppingCart) {
        Map<String, Object> response = new HashMap<>();
        boolean result = shoppingCartService.addToCart(shoppingCart);
        if (result) {
            response.put("status", 0);
            response.put("msg", "商品已成功添加到购物车");
        } else {
            response.put("status", 1);
            response.put("msg", "添加到购物车失败");
        }
        return response;
    }
    @GetMapping("/count")
    public Map<String, Object> getCartCount(@RequestParam Integer userId) {
        Map<String, Object> response = new HashMap<>();
        int count = shoppingCartService.getCartCount(userId);
        response.put("status", 0);
        response.put("count", count);
        return response;
    }

    @GetMapping("/list")
    public Map<String, Object> getCartList(@RequestParam Integer userId) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> cartList = shoppingCartService.getCartList(userId);
        response.put("status", 0);
        response.put("data", cartList);
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteCartItem(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        boolean result = shoppingCartService.deleteCartItem(id);
        if (result) {
            response.put("status", 0);
            response.put("msg", "商品已从购物车移除");
        } else {
            response.put("status", 1);
            response.put("msg", "移除商品失败");
        }
        return response;
    }

    @PutMapping("/quantity")
    public Map<String, Object> updateQuantity(@RequestParam Integer id,
                                              @RequestParam Integer quantity) {
        Map<String, Object> response = new HashMap<>();
        boolean result = shoppingCartService.updateQuantity(id, quantity);
        if (result) {
            response.put("status", 0);
            response.put("msg", "数量更新成功");
        } else {
            response.put("status", 1);
            response.put("msg", "数量更新失败");
        }
        return response;
    }

    @PostMapping("/items")
    @ResponseBody
    public Map<String, Object> getCartItems(@RequestBody Map<String, Object> params) {
        List<Integer> ids = (List<Integer>) params.get("ids");
        if (ids == null || ids.isEmpty()) {
            return Map.of("status", 1, "msg", "无效的商品ID");
        }
        List<ShoppingCart> cartItems = shoppingCartService.getCartItemsByIds(ids);
        List<Map<String, Object>> result = cartItems.stream().map(cart -> {
            System.out.println("cart.getProductId() = " + cart.getProductId());
            Products product = productsService.getProductById(cart.getProductId());
            System.out.println("查到的 product = " + product);
            Map<String, Object> item = new java.util.HashMap<>();
            item.put("id", cart.getId());
            if (product != null) {
                item.put("name", product.getName());
                item.put("iconUrl", product.getIconUrl());
                item.put("price", product.getPrice());
            } else {
                item.put("name", "商品已下架");
                item.put("iconUrl", "");
                item.put("price", 0);
            }
            item.put("quantity", cart.getQuantity());
            return item;
        }).collect(java.util.stream.Collectors.toList());
        return Map.of("status", 0, "data", result);
    }
}
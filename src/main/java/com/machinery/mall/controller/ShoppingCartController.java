package com.machinery.mall.controller;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/27  10:38
 */
import com.machinery.mall.entity.ShoppingCart;
import com.machinery.mall.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class ShoppingCartController {


    private ShoppingCartService shoppingCartService;
    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
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
        List<ShoppingCart> cartList = shoppingCartService.getCartList(userId);
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
}
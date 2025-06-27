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
}
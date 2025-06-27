package com.machinery.mall.service;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/27  10:37
 */
import com.machinery.mall.entity.ShoppingCart;

public interface ShoppingCartService {
    boolean addToCart(ShoppingCart shoppingCart);
}
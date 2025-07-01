package com.machinery.mall.service;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/27  10:37
 */
import com.machinery.mall.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    boolean addToCart(ShoppingCart shoppingCart);
    int getCartCount(Integer userId);
    List<ShoppingCart> getCartList(Integer userId);
    boolean deleteCartItem(Integer id);
    boolean updateQuantity(Integer id, Integer quantity);
}
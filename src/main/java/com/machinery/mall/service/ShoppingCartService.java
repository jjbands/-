package com.machinery.mall.service;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/27  10:37
 */
import com.machinery.mall.entity.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ShoppingCartService {
    boolean addToCart(ShoppingCart shoppingCart);
    Integer getCartCount(Integer userId);
    List<Map<String, Object>> getCartList(Integer userId);
    boolean deleteCartItem(Integer id);
    boolean updateQuantity(Integer id, Integer quantity);
    List<ShoppingCart> getCartItemsByIds(List<Integer> ids);
    ShoppingCart getCartItemById(Integer id);
}
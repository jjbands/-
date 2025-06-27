package com.machinery.mall.service;

import com.machinery.mall.entity.ShoppingCart;
import com.machinery.mall.mapper.ShoppingCartMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/27  10:37
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    public boolean addToCart(ShoppingCart shoppingCart) {
        ShoppingCart existingCartItem = shoppingCartMapper.selectByUserIdAndProductId(shoppingCart.getUserId(), shoppingCart.getProductId());
        if (existingCartItem != null) {
            // 如果购物车中已存在该商品，更新数量
            int newQuantity = existingCartItem.getQuantity() + shoppingCart.getQuantity();
            return shoppingCartMapper.updateQuantity(existingCartItem.getId(), newQuantity) > 0;
        } else {
            // 如果购物车中不存在该商品，插入新记录
            return shoppingCartMapper.insert(shoppingCart) > 0;
        }
    }
}
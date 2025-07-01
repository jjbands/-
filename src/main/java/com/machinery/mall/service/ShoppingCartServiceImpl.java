package com.machinery.mall.service;

import com.machinery.mall.entity.ShoppingCart;
import com.machinery.mall.mapper.ShoppingCartMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/27  10:37
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {


    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    public void setShoppingCartMapper(ShoppingCartMapper shoppingCartMapper) {
        this.shoppingCartMapper = shoppingCartMapper;
    }

    @Override
    public boolean addToCart(ShoppingCart shoppingCart) {
        ShoppingCart existingCartItem = shoppingCartMapper.selectByUserIdAndProductId(
                shoppingCart.getUserId(), shoppingCart.getProductId());
        if (existingCartItem != null) {
            int newQuantity = existingCartItem.getQuantity() + shoppingCart.getQuantity();
            return shoppingCartMapper.updateQuantity(existingCartItem.getId(), newQuantity) > 0;
        } else {
            return shoppingCartMapper.insert(shoppingCart) > 0;
        }
    }

    @Override
    public int getCartCount(Integer userId) {
        return shoppingCartMapper.selectCartCountByUserId(userId);
    }

    @Override
    public List<ShoppingCart> getCartList(Integer userId) {
        return shoppingCartMapper.selectCartListByUserId(userId);
    }

    @Override
    public boolean deleteCartItem(Integer id) {
        return shoppingCartMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateQuantity(Integer id, Integer quantity) {
        return shoppingCartMapper.updateQuantity(id, quantity) > 0;
    }
}
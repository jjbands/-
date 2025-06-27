package com.machinery.mall.mapper;

import com.machinery.mall.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 你的名字
 * @version 1.0.0
 * @date: 2025/06/27  10:28
 */
@Mapper
public interface ShoppingCartMapper {
    int insert(ShoppingCart shoppingCart);
    ShoppingCart selectByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
    int updateQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity);
}
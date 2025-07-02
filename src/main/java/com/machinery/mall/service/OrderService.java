package com.machinery.mall.service;

import com.machinery.mall.entity.Order;

public interface OrderService {
    /**
     * 立即购买下单
     */
    Order quickOrder(Integer userId, Integer productId, Integer quantity, Integer paymentType, Integer addressId, String province, String city, String district);

    Order getOrderById(Integer orderId);

    // 可根据需要添加更多方法
} 
package com.machinery.mall.service;

import com.machinery.mall.entity.Order;
import com.machinery.mall.entity.OrderItem;
import com.machinery.mall.entity.Products;
import com.machinery.mall.mapper.OrderItemMapper;
import com.machinery.mall.mapper.OrderMapper;
import com.machinery.mall.mapper.ProductsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;
    private ProductsMapper productsMapper;
    @Autowired
    public void setOrderMapper(OrderMapper orderMapper, OrderItemMapper orderItemMapper, ProductsMapper productsMapper) {
        this.orderMapper = orderMapper;
        this.orderItemMapper=orderItemMapper;
        this.productsMapper=productsMapper;
    }
    @Override
    @Transactional
    public Order quickOrder(Integer userId, Integer productId, Integer quantity, Integer paymentType, Integer addressId, String province, String city, String district) {
        // 查询商品
        Products product = productsMapper.selectById(productId);
        if (product == null || product.getStock() < quantity) {
            throw new RuntimeException("商品不存在或库存不足");
        }
        // 生成订单对象
        Order order = new Order();
        order.setUid(userId);
        // 生成唯一订单号
        long orderNo = System.currentTimeMillis() + userId;
        order.setOrderNo(orderNo);
        order.setAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setType(paymentType);
        order.setStatus(1); // 1-未付款
        order.setAddrId(addressId);
        order.setFreight(0); // 默认运费为0，防止为null
        order.setCreated(new Date());
        order.setUpdated(new Date());
        orderMapper.insertOrder(order);
        // 生成订单项
        OrderItem item = new OrderItem();
        item.setUid(userId);
        item.setOrderId(order.getId());
        item.setGoodsId(productId);
        item.setGoodsName(product.getName());
        item.setIconUrl(product.getIconUrl());
        item.setPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        item.setCreated(new Date());
        item.setUpdated(new Date());
        orderItemMapper.insertOrderItem(item);
        // 扣减库存
        product.setStock(product.getStock() - quantity);
        productsMapper.updateById(product);
        return order;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public Order getOrderByOrderNo(Long orderNo) {
        return orderMapper.selectOrderByOrderNo(orderNo);
    }
} 
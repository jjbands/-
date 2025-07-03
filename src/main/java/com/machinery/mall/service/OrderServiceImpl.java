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
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductsMapper productsMapper;
    @Autowired
    private ShoppingCartService shoppingCartService;

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
    public Order getOrderById(Integer id) {
        Order order = orderMapper.selectOrderById(id);
        if (order != null) {
            List<OrderItem> items = orderItemMapper.selectItemsByOrderId(id);
            order.setItems(items);
        }
        return order;
    }
    @Override
    public List<Order> getOrdersByUserId(Integer userId) {
        List<Order> orders = orderMapper.selectOrdersByUserId(userId);
        if (orders != null) {
            orders.forEach(order -> {
                List<OrderItem> items = orderItemMapper.selectItemsByOrderId(order.getId());
                order.setItems(items);
            });
        }
        return orders;
    }
    @Override
    @Transactional
    public void deleteOrder(Integer orderId) {
        // 1. 先查询订单是否存在
        Order order = orderMapper.selectOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        // 2. 恢复库存（可选）
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                Products product = productsMapper.selectById(item.getGoodsId());
                if (product != null) {
                    product.setStock(product.getStock() + item.getQuantity());
                    productsMapper.updateById(product);
                }
            }
        }

        // 3. 删除订单项
        orderItemMapper.deleteByOrderId(orderId);

        // 4. 删除订单
        orderMapper.deleteOrder(orderId);
    }
    @Override
    @Transactional
    public void updateOrderStatus(Integer orderId, Integer status) {
        Order order = orderMapper.selectOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        // 只允许从未付款(1)状态更新为已付款(2)
        if (order.getStatus() != 1 && status == 2) {
            throw new RuntimeException("只有未付款订单可以更新为已付款状态");
        }

        order.setStatus(status);
        order.setUpdated(new Date());
        orderMapper.updateOrderStatus(order);
    }

    @Override
    @Transactional
    public Order createOrder(Integer userId, Integer addressId, List<Map<String, Object>> items) {
        Order order = new Order();
        order.setUid(userId);
        order.setAddrId(addressId);
        // 生成唯一订单号
        long orderNo = System.currentTimeMillis() + userId;
        order.setOrderNo(orderNo);
        order.setType(1); // 默认类型，1-普通订单/支付方式
        order.setStatus(1); // 待付款
        order.setCreated(new java.util.Date());
        order.setUpdated(new java.util.Date());
        order.setFreight(0); // 默认运费为0
        // 计算订单总金额
        java.math.BigDecimal totalAmount = java.math.BigDecimal.ZERO;
        for (Map<String, Object> item : items) {
            Integer cartId = Integer.parseInt(item.get("productId").toString()); // 实际为购物车id
            Integer quantity = Integer.parseInt(item.get("quantity").toString());
            com.machinery.mall.entity.ShoppingCart cart = shoppingCartService.getCartItemById(cartId);
            if (cart == null) {
                throw new RuntimeException("购物车项ID " + cartId + " 不存在");
            }
            Integer productId = cart.getProductId();
            com.machinery.mall.entity.Products product = productsMapper.selectById(productId);
            if (product == null) {
                throw new RuntimeException("商品ID " + productId + " 不存在或已下架");
            }
            totalAmount = totalAmount.add(product.getPrice().multiply(java.math.BigDecimal.valueOf(quantity)));
        }
        order.setAmount(totalAmount);
        orderMapper.insertOrder(order);
        for (Map<String, Object> item : items) {
            Integer cartId = Integer.parseInt(item.get("productId").toString()); // 实际为购物车id
            Integer quantity = Integer.parseInt(item.get("quantity").toString());
            com.machinery.mall.entity.ShoppingCart cart = shoppingCartService.getCartItemById(cartId);
            if (cart == null) {
                throw new RuntimeException("购物车项ID " + cartId + " 不存在");
            }
            Integer productId = cart.getProductId();
            com.machinery.mall.entity.Products product = productsMapper.selectById(productId);
            if (product == null) {
                throw new RuntimeException("商品ID " + productId + " 不存在或已下架");
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(userId);
            orderItem.setOrderId(order.getId());
            orderItem.setGoodsId(productId);
            orderItem.setGoodsName(product.getName());
            orderItem.setIconUrl(product.getIconUrl());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(quantity);
            orderItem.setTotalPrice(product.getPrice().multiply(java.math.BigDecimal.valueOf(quantity)));
            orderItem.setCreated(new java.util.Date());
            orderItem.setUpdated(new java.util.Date());
            orderItemMapper.insertOrderItem(orderItem);
        }
        return order;
    }
}
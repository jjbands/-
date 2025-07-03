package com.machinery.mall.controller;

import com.machinery.mall.entity.Order;
import com.machinery.mall.entity.OrderItem;
import com.machinery.mall.entity.UserAddress;
import com.machinery.mall.service.OrderService;
import com.machinery.mall.mapper.OrderItemMapper;
import com.machinery.mall.mapper.UserAddressMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 立即购买下单接口
     */
    @PostMapping("/quick")
    public Map<String, Object> quickOrder(@RequestBody Map<String, Object> params) {
        // 从请求体获取 userId
        Integer userId = null;
        if (params.get("userId") != null) {
            userId = Integer.parseInt(params.get("userId").toString());
        }
        if (userId == null) {
            throw new RuntimeException("请先登录");
        }
        Integer productId = (Integer) params.get("productId");
        Integer quantity = (Integer) params.get("quantity");
        Integer paymentType = params.get("paymentMethod") == null ? 1 : Integer.parseInt(params.get("paymentMethod").toString());
        String province = (String) params.get("deliveryProvince");
        String city = (String) params.get("deliveryCity");
        String district = (String) params.get("deliveryDistrict");
        Integer addressId = params.get("addressId") == null ? null : Integer.parseInt(params.get("addressId").toString());
        Order order = orderService.quickOrder(userId, productId, quantity, paymentType, addressId, province, city, district);
        order.setFreight(0); // 默认运费为0，如有实际运费逻辑可替换
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getId());
        result.put("status", "success");
        return result;
    }

    @PostMapping("/buyNow")
    public Map<String, Object> buyNow(@RequestBody Map<String, Object> params) {
        Integer userId = params.get("userId") == null ? null : Integer.parseInt(params.get("userId").toString());
        Integer productId = params.get("productId") == null ? null : Integer.parseInt(params.get("productId").toString());
        Integer quantity = params.get("quantity") == null ? 1 : Integer.parseInt(params.get("quantity").toString());
        Integer payType = params.get("payType") == null ? 1 : Integer.parseInt(params.get("payType").toString());
        Integer addrId = params.get("addrId") == null ? null : Integer.parseInt(params.get("addrId").toString());
        Map<String, Object> result = new HashMap<>();
        try {
            Order order = orderService.quickOrder(userId, productId, quantity, payType, addrId, null, null, null);
            result.put("status", 0);
            result.put("msg", "下单成功");
            result.put("data", order.getOrderNo());
        } catch (Exception e) {
            result.put("status", 1);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/detail")
    public Map<String, Object> getOrderDetail(@RequestParam("orderNo") Long orderNo) {
        Map<String, Object> result = new HashMap<>();
        try {
            Order order = orderService.getOrderByOrderNo(orderNo);
            if (order == null) {
                result.put("status", 1);
                result.put("msg", "订单不存在");
                return result;
            }
            List<OrderItem> items = orderItemMapper.selectItemsByOrderId(order.getId());
            UserAddress address = userAddressMapper.selectById(order.getAddrId());
            Map<String, Object> data = new HashMap<>();
            data.put("orderNo", order.getOrderNo());
            data.put("created", order.getCreated());
            data.put("status", order.getStatus());
            data.put("statusText", getStatusText(order.getStatus()));
            data.put("amount", order.getAmount());
            data.put("address", address);
            data.put("items", items);
            result.put("status", 0);
            result.put("msg", "查询成功");
            result.put("data", data);
        } catch (Exception e) {
            result.put("status", 1);
            result.put("msg", "查询失败: " + e.getMessage());
        }
        return result;
    }

    private String getStatusText(Integer status) {
        switch (status) {
            case 1: return "未付款";
            case 2: return "已付款";
            case 3: return "已发货";
            case 4: return "交易成功";
            case 5: return "交易关闭";
            case 6: return "已取消";
            default: return "未知";
        }
    }

    // 可根据需要添加订单查询等接口
} 
package com.machinery.mall.controller;

import com.machinery.mall.entity.Order;
import com.machinery.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 立即购买下单接口
     */
   // ... existing code ...
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
// ... existing code ...
    // 可根据需要添加订单查询等接口
} 
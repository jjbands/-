package com.machinery.mall.controller;

import com.machinery.mall.entity.Order;
import com.machinery.mall.entity.UserAddress;
import com.machinery.mall.service.OrderService;
import com.machinery.mall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("/quick")
    @ResponseBody
    public Map<String, Object> quickOrder(@RequestBody Map<String, Object> params) {
        try {
            int userId = parseNumber(params.get("userId"), "用户ID").intValue();
            if (userId <= 0) {
                throw new IllegalArgumentException("请先登录");
            }

            int productId = parseNumber(params.get("productId"), "商品ID").intValue();
            int quantity = parseNumber(params.get("quantity"), "购买数量").intValue();
            int paymentType = parseNumber(params.get("paymentMethod"), "支付方式", 1).intValue();
            Integer addressId = params.get("addressId") != null ?
                    parseNumber(params.get("addressId"), "地址ID").intValue() : null;

            String province = getString(params, "deliveryProvince");
            String city = getString(params, "deliveryCity");
            String district = getString(params, "deliveryDistrict");

            Order order = orderService.quickOrder(
                    userId, productId, quantity, paymentType,
                    addressId, province, city, district
            );
            order.setFreight(0);

            return Map.of(
                    "status", "success",
                    "orderId", order.getId()
            );

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("订单创建失败", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "系统繁忙");
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getOrderApi(@PathVariable Integer id) {
        try {
            Order order = orderService.getOrderById(id);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("status", "error", "message", "订单不存在")
                );
            }
            // 获取用户地址信息
            String formattedAddress = getFormattedAddress(order);
            order.setAddress(formattedAddress);

            return ResponseEntity.ok(
                    Map.of("status", "success", "data", order)
            );
        } catch (Exception e) {
            log.error("获取订单详情失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", e.getMessage()
                    ));
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable Integer id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "订单删除成功"
            ));
        } catch (Exception e) {
            log.error("删除订单失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", e.getMessage()
                    ));
        }
    }

    // 格式化地址信息
    private String getFormattedAddress(Order order) {
        UserAddress address = null;

        // 1. 尝试使用订单中的地址ID获取地址
        if (order.getAddrId() != null) {
            address = userAddressService.getAddressById(order.getAddrId());
        }

        // 2. 如果没有地址ID或地址不存在，尝试获取用户默认地址
        if (address == null) {
            address = userAddressService.getDefaultAddressByUserId(order.getUid());
        }

        // 3. 格式化地址信息
        if (address != null) {
            return formatAddress(address);
        }

        return null;
    }

    private String formatAddress(UserAddress address) {
        StringBuilder sb = new StringBuilder();

        // 添加省市区信息
        if (address.getProvince() != null) sb.append(address.getProvince());
        if (address.getCity() != null) sb.append(address.getCity());
        if (address.getDistrict() != null) sb.append(address.getDistrict());
        if (address.getAddr() != null) sb.append(address.getAddr());

        // 添加收件人信息
        if (address.getName() != null || address.getMobile() != null) {
            sb.append(" (");
            if (address.getName() != null) sb.append(address.getName());
            if (address.getMobile() != null) {
                if (address.getName() != null) sb.append(", ");
                sb.append(address.getMobile());
            }
            sb.append(")");
        }

        return sb.toString();
    }

    @GetMapping("/view")
    public String orderDetailPage(@RequestParam Integer id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUserOrders(@PathVariable Integer userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);

            // 为每个订单获取地址信息
            for (Order order : orders) {
                String formattedAddress = getFormattedAddress(order);
                order.setAddress(formattedAddress);
            }

            return ResponseEntity.ok(
                    Map.of("status", "success", "data", orders)
            );
        } catch (Exception e) {
            log.error("获取用户订单列表失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "获取订单列表失败"));
        }
    }

    @PostMapping("/checkout")
    @ResponseBody
    public Map<String, Object> checkout(@RequestBody Map<String, Object> params) {
        try {
            int userId = parseNumber(params.get("userId"), "用户ID").intValue();
            Integer addressId = params.get("addressId") != null ?
                    parseNumber(params.get("addressId"), "地址ID").intValue() : null;
            List<Map<String, Object>> items = (List<Map<String, Object>>) params.get("items");
            if (items == null || items.isEmpty()) {
                throw new IllegalArgumentException("请选择要结算的商品");
            }
            Order order = orderService.createOrder(userId, addressId, items);
            return Map.of("status", "success", "orderId", order.getId());
        } catch (Exception e) {
            return Map.of("status", "error", "msg", e.getMessage());
        }
    }

    private Number parseNumber(Object value, String fieldName) {
        return parseNumber(value, fieldName, null);
    }

    private Number parseNumber(Object value, String fieldName, Integer defaultValue) {
        if (value == null) {
            if (defaultValue != null) return defaultValue;
            throw new IllegalArgumentException(fieldName + "不能为空");
        }

        try {
            if (value instanceof Number) {
                return (Number) value;
            }
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + "必须是有效数字");
        }
    }

    private String getString(Map<String, Object> params, String key) {
        Object value = params.get(key);
        return value != null ? value.toString() : null;
    }
    @PutMapping("/{id}/status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateOrderStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> params) {
        try {
            Integer status = parseNumber(params.get("status"), "订单状态").intValue();
            orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "订单状态更新成功"
            ));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("更新订单状态失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", e.getMessage()
                    ));
        }
    }
}
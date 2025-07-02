package com.machinery.mall.mapper;

import com.machinery.mall.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    int insertOrderItem(OrderItem item);
    List<OrderItem> selectItemsByOrderId(@Param("orderId") Integer orderId);
    // 可根据需要添加更多方法
} 
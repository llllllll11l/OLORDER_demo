package com.example.demo.dao;

import com.example.demo.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    List<OrderItem> selectByOrderId(String orderId);
    OrderItem selectByOrderIdAndProductId(@Param("orderId")String orderId, /*ProductId->productId*/@Param("productId")String productId);
    OrderItem selectByOrderItemId(String orderItemId);
    int updateByOrderItemId(OrderItem orderItem);
    int insertSelective(OrderItem orderItem);
    int deleteByOrderItemId(String orderItemId);
}

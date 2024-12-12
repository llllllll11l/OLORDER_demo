package com.example.demo.dao;

import com.example.demo.entity.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    List<OrderItem> selectByOrderId(String orderId);
    int insertSelective(OrderItem orderItem);
    int deleteByOrderItemId(String orderItemId);
}

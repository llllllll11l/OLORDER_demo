package com.example.demo.dao;

import com.example.demo.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    List<OrderItem> selectByOrderId(String orderId);
    OrderItem selectByOrderIdAndProductId(@Param("orderId")String orderId, @Param("ProductId")String ProductId);
    OrderItem selectByOrderItemId(String orderItemId);
    int insertSelective(OrderItem orderItem);
    int deleteByOrderItemId(String orderItemId);
}

package com.example.demo.dao;

import org.springframework.core.annotation.Order;

import java.util.List;

public interface OrderMapper {
    Order selectByOrderId(String orderId);
    List<Order> selectByUserId(String userId);
    List<Order> selectByStoreId(String storeId);//先不用实现
    int insertSelective(Order order);
    int deleteByOrderId(String orderId);
    int updateByOrderId(String orderId);
}

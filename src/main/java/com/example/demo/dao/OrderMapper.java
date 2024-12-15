package com.example.demo.dao;

import com.example.demo.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    Order selectByOrderId(String orderId);
    List<Order> selectByUserId(String userId);
    List<Order> selectByStoreId(String storeId);//先不用实现
    List<Order> selectByUserIdAndStoreId(@Param("userId") String userId, @Param("storeId") String storeId);
    int insertSelective(Order order);
    int deleteByOrderId(String orderId);
    int updateByOrderId(String orderId);
}

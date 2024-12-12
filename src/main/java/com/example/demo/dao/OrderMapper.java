package com.example.demo.dao;

import org.apache.ibatis.annotations.*;
import com.example.demo.entity.Orders;

import java.util.List;

public interface OrderMapper {
    @Select("SELECT * FROM orders WHERE order_id = #{orderId}")
    Orders selectByOrderId(@Param("orderId") String orderId);

    @Select("SELECT * FROM orders WHERE customer_id = #{userId}")
    List<Orders> selectByUserId(@Param("userId") String userId);

    List<Orders> selectByStoreId(String storeId);//先不用实现

    @Insert("<script>" +
            "INSERT INTO orders (" +
            "<if test='orderId != null'>order_id,</if>" +
            "<if test='customerId != null'>customer_id,</if>" +
            "<if test='storeId != null'>store_id,</if>" +
            "<if test='totalAmount != null'>total_amount,</if>" +
            "<if test='orderStatus != null'>order_status,</if>" +
            "<if test='deliveryAddress != null'>delivery_address,</if>" +
            "<if test='orderDate != null'>order_date,</if>" +
            "<if test='createAt != null'>create_at,</if>" +
            "<if test='updateAt != null'>update_at,</if>" +
            ") " +
            "VALUES (" +
            "<if test='orderId != null'>#{orderId},</if>" +
            "<if test='customerId != null'>#{customerId},</if>" +
            "<if test='storeId != null'>#{storeId},</if>" +
            "<if test='totalAmount != null'>#{totalAmount},</if>" +
            "<if test='orderStatus != null'>#{orderStatus},</if>" +
            "<if test='deliveryAddress != null'>#{deliveryAddress},</if>" +
            "<if test='orderDate != null'>#{orderDate},</if>" +
            "<if test='createAt != null'>#{createAt},</if>" +
            "<if test='updateAt != null'>#{updateAt},</if>" +
            ") " +
            "</script>")
    int insertSelective(Orders order);

    @Delete("DELETE FROM orders WHERE order_id = #{orderId}")
    int deleteByOrderId(@Param("orderId") String orderId);

    @Update("<script>" +
            "UPDATE orders SET " +
            "<if test='customerId != null'>customer_id = #{customerId},</if>" +
            "<if test='storeId != null'>store_id = #{storeId},</if>" +
            "<if test='totalAmount != null'>total_amount = #{totalAmount},</if>" +
            "<if test='orderStatus != null'>order_status = #{orderStatus},</if>" +
            "<if test='deliveryAddress != null'>delivery_address = #{deliveryAddress},</if>" +
            "<if test='orderDate != null'>order_date = #{orderDate},</if>" +
            "<if test='createAt != null'>create_at = #{createAt},</if>" +
            "<if test='updateAt != null'>update_at = #{updateAt},</if>" +
            "WHERE order_id = #{orderId}" +
            "</script>")
    int updateByOrderId(@Param("orderId") String orderId);
}

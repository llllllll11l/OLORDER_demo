package com.example.demo.dao;

import com.example.demo.entity.OrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderItemMapper {
    @Select("SELECT * FROM order_items WHERE order_id = #{orderId}")
    List<OrderItem> selectByOrderId(@Param("orderId") String orderId);

    @Insert("<script>" +
            "INSERT INTO order_items (" +
            "<if test='orderItemsId != null'>order_items_id,</if>" +
            "<if test='orderId != null'>order_id,</if>" +
            "<if test='productId != null'>product_id,</if>" +
            "<if test='quantity >= 0'>quantity,</if>" +
            "<if test='price != null'>price,</if>" +
            "<if test='totalPrice != null'>total_price,</if>" +
            ") " +
            "VALUES (" +
            "<if test='orderItemsId != null'>#{orderItemsId},</if>" +
            "<if test='orderId != null'>#{orderId},</if>" +
            "<if test='productId != null'>#{productId},</if>" +
            "<if test='quantity >= 0'>#{quantity},</if>" +
            "<if test='price != null'>#{price},</if>" +
            "<if test='totalPrice != null'>#{totalPrice},</if>" +
            ") " +
            "</script>")
    int insertSelective(OrderItem orderItem);

    @Delete("DELETE FROM order_items WHERE order_item_id = #{orderItemId}")
    int deleteByOrderItemId(@Param("orderItemId") String orderItemId);
}

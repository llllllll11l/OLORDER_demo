package com.example.demo.dao;

import com.example.demo.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductMapper {
    @Select("SELECT * FROM products WHERE product_id = #{productId}")
    Product selectByProductId(@Param("productId") String productId);

    @Select("SELECT * FROM products WHERE store_id = #{storeId}")
    List<Product> selectByStoreId(@Param("storeId") String storeId);

    @Insert("<script>" +
            "INSERT INTO products (" +
            "<if test='productID != null'>product_id,</if>" +
            "<if test='productName != null'>product_name,</if>" +
            "<if test='productDescription != null'>product_description,</if>" +
            "<if test='productCategory != null'>product_category,</if>" +
            "<if test='price != null'>price,</if>" +
            "<if test='stockQuantity >= 0'>stock_quantity,</if>" +
            "<if test='productStatus != null'>product_status,</if>" +
            "<if test='productImage != null'>product_image,</if>" +
            "<if test='createDate != null'>create_date,</if>" +
            "<if test='updateDate != null'>update_date,</if>" +
            "<if test='storeID != null'>store_id,</if>" +
            ") " +
            "VALUES (" +
            "<if test='productID != null'>#{productID},</if>" +
            "<if test='productName != null'>#{productName},</if>" +
            "<if test='productDescription != null'>#{productDescription},</if>" +
            "<if test='productCategory != null'>#{productCategory},</if>" +
            "<if test='price != null'>#{price},</if>" +
            "<if test='stockQuantity >= 0'>#{stockQuantity},</if>" +
            "<if test='productStatus != null'>#{productStatus},</if>" +
            "<if test='productImage != null'>#{productImage},</if>" +
            "<if test='createDate != null'>#{createDate},</if>" +
            "<if test='updateDate != null'>#{updateDate},</if>" +
            "<if test='storeID != null'>#{storeID},</if>" +
            ") " +
            "</script>")
    int insertSelective(Product product);

    @Delete("DELETE FROM products WHERE product_id = #{productId}")
    int deleteByProductId(String productId);

    @Update("<script>" +
            "UPDATE products SET " +
            "<if test='productName != null'>product_name = #{productName},</if>" +
            "<if test='productDescription != null'>product_description = #{productDescription},</if>" +
            "<if test='productCategory != null'>product_category = #{productCategory},</if>" +
            "<if test='price != null'>price = #{price},</if>" +
            "<if test='stockQuantity >= 0'>stock_quantity = #{stockQuantity},</if>" +
            "<if test='productStatus != null'>product_status = #{productStatus},</if>" +
            "<if test='productImage != null'>product_image = #{productImage},</if>" +
            "<if test='createDate != null'>create_date = #{createDate},</if>" +
            "<if test='updateDate != null'>update_date = #{updateDate},</if>" +
            "<if test='storeID != null'>store_id = #{storeID},</if>" +
            "WHERE product_id = #{productID}" +
            "</script>")
    int updateByProductId(Product product);
}

package com.example.demo.dao;

import com.example.demo.entity.StoreReview;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StoreReviewMapper {

    @Select("SELECT * FROM store_reviews WHERE review_id = #{reviewId}")
    StoreReview selectByReviewId(@Param("reviewId") String reviewId);


    @Select("SELECT * FROM store_reviews WHERE store_id = #{storeId}")
    List<StoreReview> selectByStoreId(@Param("storeId") String storeId);

    @Insert("<script>" +
            "INSERT INTO store_reviews (" +
            "<if test='reviewID != null'>review_id,</if>" +
            "<if test='orderId != null'>order_id,</if>" +
            "<if test='storeId != null'>store_id,</if>" +
            "<if test='customerID != null'>customer_id,</if>" +
            "<if test='rating != null'>rating,</if>" +
            "<if test='comment != null'>comment,</if>" +
            "<if test='reviewDate != null'>review_date,</if>" +
            "<if test='status != null'>status,</if>" +
            "<if test='response != null'>response,</if>" +
            "<if test='responseDate != null'>response_date,</if>" +
            ") " +
            "VALUES (" +
            "<if test='reviewID != null'>#{reviewID},</if>" +
            "<if test='orderId != null'>#{orderId},</if>" +
            "<if test='storeId != null'>#{storeId},</if>" +
            "<if test='customerID != null'>#{customerID},</if>" +
            "<if test='rating != null'>#{rating},</if>" +
            "<if test='comment != null'>#{comment},</if>" +
            "<if test='reviewDate != null'>#{reviewDate},</if>" +
            "<if test='status != null'>#{status},</if>" +
            "<if test='response != null'>#{response},</if>" +
            "<if test='responseDate != null'>#{responseDate},</if>" +
            ") " +
            "</script>")
    int insertSelective(StoreReview storeReview);


    @Delete("DELETE FROM store_reviews WHERE review_id = #{reviewId}")
    int deleteByReviewId(@Param("reviewId") String reviewId);

    @Update("<script>" +
            "UPDATE store_reviews SET " +
            "<if test='orderId != null'>order_id = #{orderId},</if>" +
            "<if test='storeId != null'>store_id = #{storeId},</if>" +
            "<if test='customerID != null'>customer_id = #{customerID},</if>" +
            "<if test='rating != null'>rating = #{rating},</if>" +
            "<if test='comment != null'>comment = #{comment},</if>" +
            "<if test='reviewDate != null'>review_date = #{reviewDate},</if>" +
            "<if test='status != null'>status = #{status},</if>" +
            "<if test='response != null'>response = #{response},</if>" +
            "<if test='responseDate != null'>response_date = #{responseDate},</if>" +
            "WHERE review_id = #{reviewID}" +
            "</script>")
    int updateByReviewId(String reviewId);
}

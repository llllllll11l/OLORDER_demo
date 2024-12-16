package com.example.demo.dao;

import com.example.demo.entity.StoreReview;

import java.util.List;

public interface StoreReviewMapper {
    StoreReview selectByReviewId(String reviewId);
    List<StoreReview> selectByStoreId(String storeId);
    int insertSelective(StoreReview storeReview);
    int deleteByReviewId(String storeId);
    int updateByReviewId(StoreReview storeReview);
}

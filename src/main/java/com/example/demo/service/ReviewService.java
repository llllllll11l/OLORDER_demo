package com.example.demo.service;

import com.example.demo.controller.Param.StoreReviewAddParam;

public interface ReviewService {

    /**
     * 添加店铺评论
     *
     * @param userId
     * @param storeId
     * @param storeReviewAddParam
     * @return
     */
    String addStoreReview(String userId, String storeId, StoreReviewAddParam storeReviewAddParam);
}

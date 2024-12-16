package com.example.demo.service.impl;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Enums.StoreReviewStatus;
import com.example.demo.controller.Param.StoreReviewAddParam;
import com.example.demo.dao.StoreReviewMapper;
import com.example.demo.entity.StoreReview;
import com.example.demo.service.ReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    StoreReviewMapper storeReviewMapper;
    @Override
    public String addStoreReview(String userId, String storeId, StoreReviewAddParam storeReviewAddParam) {
        StoreReview storeReview=new StoreReview();
        storeReview.setReviewID(UUID.randomUUID().toString());
        storeReview.setStoreId(storeId);
        storeReview.setCustomerID(userId);
        storeReview.setReviewDate(new Timestamp(System.currentTimeMillis()));
        storeReview.setStatus(StoreReviewStatus.NORMAL);
        BeanUtils.copyProperties(storeReviewAddParam,storeReview);
        if(storeReviewMapper.insertSelective(storeReview)>0){
            return storeReview.getReviewID();
        }
        return ServiceResultEnum.ADD_STORE_REVIEW_FAILED.getResult();
    }
}

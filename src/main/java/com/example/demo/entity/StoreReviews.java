package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StoreReviews {
    private String reviewID;
    private String orderId;
    private String storeId;
    private String customerID;
    private int rating;
    private String comment;
    private Timestamp reviewDate;
    private StoreReviewStatus status;
    private String response;
    private Timestamp responseDate;

}

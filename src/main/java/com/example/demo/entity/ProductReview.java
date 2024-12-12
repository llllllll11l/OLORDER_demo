package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductReview {
    private String reviewID;
    private String orderId;
    private String productId;
    private String customerID;
    private int rating;
    private String comment;
    private Timestamp reviewDate;
    private ProductReviewStatus status;
    private String response;
    private Timestamp responseDate;
}

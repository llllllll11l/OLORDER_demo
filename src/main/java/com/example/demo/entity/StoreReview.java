package com.example.demo.entity;

import com.example.demo.Enums.StoreReviewStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class StoreReview {
    private String reviewID;
    private String orderId;/*delete this one?*/
    private String storeId;
    private String customerID;
    private int rating;
    private String comment;
    private Timestamp reviewDate;
    private StoreReviewStatus status;
    private String response;
    private Timestamp responseDate;

}

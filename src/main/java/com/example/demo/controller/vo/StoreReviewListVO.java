package com.example.demo.controller.vo;

import com.example.demo.Enums.StoreReviewStatus;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class StoreReviewListVO implements Serializable {
    private String reviewID;
    private String orderId;
    private String storeId;
    private String customerID;
    private int rating;
    private String comment;
    private Timestamp reviewDate;
    private String response;
    private Timestamp responseDate;
}

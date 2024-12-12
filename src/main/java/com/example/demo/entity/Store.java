package com.example.demo.entity;

import java.sql.Timestamp;

import com.example.demo.Enums.StoreStatus;
import com.example.demo.Enums.StoreType;
import lombok.Data;

@Data
public class Store {
    private String storeId;
    private String storeName;
    private String storeDescription;
    private String storeAddress;
    private String contactNumber;
    private StoreType storeType;
    private StoreStatus storeStatus;
    private String verificationDocs;
    private String ownerID;
    private Timestamp createdAt;
    private Timestamp updateAt;
    private int rating;
    private int visited;
}

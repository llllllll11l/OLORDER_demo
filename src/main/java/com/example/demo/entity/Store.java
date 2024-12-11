package com.example.demo.entity;

import java.sql.Timestamp;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

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
}

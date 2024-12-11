package com.example.demo.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StoreStatusHistory {
    private String historyID;
    private String storeId;
    private StoreHistoryStatus status;
    private String remarks;
    private Timestamp changedAt;
}

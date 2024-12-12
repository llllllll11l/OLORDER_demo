package com.example.demo.entity;

import com.example.demo.Enums.StoreHistoryStatus;
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

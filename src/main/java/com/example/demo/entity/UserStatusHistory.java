package com.example.demo.entity;

import com.example.demo.Enums.UserHistoryStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserStatusHistory {
    private String historyID;
    private String userId;
    private UserHistoryStatus status;
    private String remarks;
    private Timestamp changedAt;
}

package com.example.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
public class UserStatusHistory {
    private String historyID;
    private String userId;
    private UserHistoryStatus status;
    private String remarks;
    private Timestamp changedAt;
}

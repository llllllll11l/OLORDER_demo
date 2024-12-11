package com.example.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
public class UserToken {
    private String tokenId;
    private String userId;
    private String token;
    private Timestamp expireTime;
    private Timestamp updateTime;
}

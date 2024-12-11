package com.example.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserToken {
    private String tokenId;
    private String userId;
    private String token;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expireTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateTime;
}

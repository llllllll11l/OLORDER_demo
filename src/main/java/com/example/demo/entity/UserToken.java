package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserToken {
    private String tokenId;
    private String userId;
    private String token;
    private Date expireTime;
    private Date updateTime;
}

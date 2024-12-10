package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String userId;
    private String username;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private String profilePicture;
    private Date registrationDate;
    private Date lastLoginDate;
    private Date lastPasswordChange;
    private UserStatus status;
    Boolean isEmailVerified;
    Boolean isPhoneVerified;
}

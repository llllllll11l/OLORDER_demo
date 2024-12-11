package com.example.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registrationDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date lastLoginDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date lastPasswordChange;
    private UserStatus status;
    Boolean isEmailVerified;
    Boolean isPhoneVerified;
}

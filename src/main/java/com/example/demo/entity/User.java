package com.example.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
public class User {
    private String userId;
    private String username;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private String profilePicture;
    private Timestamp registrationDate;
    private Timestamp lastLoginDate;
    private Timestamp lastPasswordChange;
    private UserStatus status;
    Boolean isEmailVerified;
    Boolean isPhoneVerified;
}

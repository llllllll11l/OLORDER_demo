package com.example.demo.entity;


import com.example.demo.Enums.AdminStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Admin {
    private String adminID;
    private String adminName;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private String profilePicture;
    private Date registrationDate;
    private Date lastLoginDate;
    private AdminStatus status;
    private Timestamp LastPasswordChange;
}

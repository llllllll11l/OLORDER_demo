package com.example.demo.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Admins {
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
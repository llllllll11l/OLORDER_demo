package com.example.demo.controller.vo;

import com.example.demo.entity.UserStatus;
import com.example.demo.entity.UserType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserVO {
    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private String profilePicture;
}

package com.example.demo.controller.vo;

import com.example.demo.Enums.UserType;
import lombok.Data;

@Data
public class UserVO {
    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private String profilePicture;
}

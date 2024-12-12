package com.example.demo.controller.vo;

import com.example.demo.Enums.UserType;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private String profilePicture;
}

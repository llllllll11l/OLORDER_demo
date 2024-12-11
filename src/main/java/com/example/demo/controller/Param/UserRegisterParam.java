package com.example.demo.controller.Param;

import com.example.demo.entity.UserStatus;
import com.example.demo.entity.UserType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserRegisterParam {
    private String username;
    private String passwordHash;
    private UserType userType;
}

package com.example.demo.controller.Param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateInfoParam implements Serializable {
    private String username;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private String profilePicture;

    public UserUpdateInfoParam(){}
}

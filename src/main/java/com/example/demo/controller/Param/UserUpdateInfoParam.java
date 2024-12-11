package com.example.demo.controller.Param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateInfoParam implements Serializable {
    @Schema(description = "")
    private String username;
    @Schema(description = "")
    private String email;
    @Schema
    private String phoneNumber;
    @Schema
    private String passwordHash;
    @Schema
    private String profilePicture;
}

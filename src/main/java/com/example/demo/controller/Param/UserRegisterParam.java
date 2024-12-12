package com.example.demo.controller.Param;

import com.example.demo.Enums.UserType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterParam implements Serializable {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String passwordHash;
    @NotEmpty(message = "用户类型不能为空")
    private UserType userType;

    public UserRegisterParam(){}
}

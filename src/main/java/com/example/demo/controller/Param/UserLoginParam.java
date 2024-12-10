package com.example.demo.controller.Param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginParam implements Serializable {
    @Schema(description = "登陆名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @NotEmpty(message = "密码不能为空")
    private String passwordHash;
}

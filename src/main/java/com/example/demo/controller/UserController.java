package com.example.demo.controller;


import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.controller.Param.UserLoginParam;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.dao.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;
import java.util.List;

@RestController
@Tag(name="用户接口", description = "")
@RequestMapping("/api/v1")
public class UserController {

    @Autowired UserMapper userMapper;

    @Resource private UserService userService;

    private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping("/user/login")
    @Operation(summary = "登陆接口", description = "用户登陆")
    public Result login(@RequestBody @Valid UserLoginParam userLoginParam){
        ServiceResultEnum serviceResult=userService.login(userLoginParam.getUsername(),userLoginParam.getPasswordHash());

        logger.info("login api, username={}, loginResult={}",userLoginParam.getUsername(), serviceResult.getResult());

        if(serviceResult==ServiceResultEnum.LOGIN_SUCCESSED_NEW_TOKEN||serviceResult==ServiceResultEnum.LOGIN_SUCCESSED_UPDATE_TOKEN){
            Result result= ResultGenerator.genSuccessResult();
            result.setData(serviceResult.getResult());
            return result;
        }
        return ResultGenerator.genFailResult(serviceResult.getResult());
    }

    @GetMapping("/user/findAll")
    @Operation(summary="查找所有用户测试", description = "1111")
    public List<User> findAll(){
        return userMapper.findAllUsers();
    }

    @GetMapping("/user/info")
    @Operation(summary="获取用户信息", description = "")
    public Result getUserDetail(){return null;}

    @GetMapping("/test")
    public String test(){
        return "hello??";
    }
}

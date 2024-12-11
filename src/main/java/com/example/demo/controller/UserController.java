package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.controller.Param.UserLoginParam;
import com.example.demo.controller.Param.UserRegisterParam;
import com.example.demo.controller.Param.UserUpdateInfoParam;
import com.example.demo.controller.vo.UserVO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.dao.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.Enums.ServiceResultEnum.*;

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
    @Operation(summary="查找所有用户接口", description = "Show me EVERYTHING!!!")
    public List<User> findAll(){
        return userMapper.findAllUsers();
    }

    @GetMapping("/user/info")
    @Operation(summary="获取用户信息", description = "")
    public Result getUserDetail(@TokenRequired User user){
        if (user == null) {
            //
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResultGenerator.genSuccessResult(userVO);
    }

    @PutMapping("/user/updinfo")
    @Operation(summary="update", description = "")
    public Result updateInfo(@RequestBody @Parameter(name="用户信息")UserUpdateInfoParam userUpdateInfoParam,
                             @TokenRequired User user){
        ServiceResultEnum serviceResult=userService.updateInfo(userUpdateInfoParam, user.getUserId());
        if(serviceResult== UPDATE_FAILED){
            return ResultGenerator.genFailResult(UPDATE_FAILED.getResult());
        }
        else if(serviceResult==SUCCESS){
            return ResultGenerator.genSuccessResult(SUCCESS.getResult());
        }
        return null;
    }

    @PostMapping("/user/logout")
    @Operation(summary = "登出接口", description = "登出，清除token")
    public Result logout(@TokenRequired User user){
        ServiceResultEnum serviceResultEnum=userService.logout(user.getUserId());
        logger.info("logout api, loginUser={}", user.getUserId());
        if(serviceResultEnum==SUCCESS){
            return ResultGenerator.genSuccessResult();
        }
        else if(serviceResultEnum==LOGOUT_FAILED){
            return ResultGenerator.genFailResult(LOGOUT_FAILED.getResult());
        }
        return null;
    }

    @PostMapping("/user/register")
    @Operation(summary = "用户注册", description = "")
    public Result register(@RequestBody @Valid UserRegisterParam userRegisterParam) {
        ServiceResultEnum registerResult = userService.register(userRegisterParam.getUsername(), userRegisterParam.getPasswordHash(), userRegisterParam.getUserType());
        logger.info("register api,username={},loginResult={}", userRegisterParam.getUsername(), registerResult);

        if (registerResult==SUCCESS) {
            return ResultGenerator.genSuccessResult();
        }
        else if(registerResult==REGISTER_FAILED) {
            return ResultGenerator.genFailResult(REGISTER_FAILED.getResult());
        }
        return null;
    }
}

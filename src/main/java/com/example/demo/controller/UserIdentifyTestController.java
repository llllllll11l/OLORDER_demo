package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.dao.UserTokenMapper;
import com.example.demo.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="用户身份验证测试", description="")
@RequestMapping("/api/v1")
public class UserIdentifyTestController {
    @Autowired UserTokenMapper userTokenMapper;
    @GetMapping("/IdentifyTest")
    @Operation(summary="Token测试接口", description="只测试Token是否有效，不验证用户登陆信息")
    public Result identifyTest(@TokenRequired User user){
        //if(user!=null){
        //    System.out.println("---identifying user id  "+user.getUserId()+"  naming  "+user.getUsername());
        //}
        Result result=null;
        if(user==null){
            result=ResultGenerator.genErrorResult(416,"用户未登陆！");
        }
        else{
            result=ResultGenerator.genSuccessResult("登陆Token验证通过！");
        }
        return result;
    }

    @GetMapping("/NotIdentifyTest")
    @Operation(summary="无需验证测试接口", description="")
    public Result notIdentifyTest(){
        return ResultGenerator.genSuccessResult("无需登陆验证");
    }
}

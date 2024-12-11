package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="用户身份验证测试", description="")
@RequestMapping("/api/v1")
public class UserIdentifyTestController {
    @GetMapping("/IdentifyTest")
    @Operation(summary="测试接口", description="")
    public Result identifyTest(@TokenRequired User user){
        Result result=null;
        if(user==null){
            result=ResultGenerator.genErrorResult(416,"用户未登陆！");
        }
        else{
            result=ResultGenerator.genSuccessResult("登陆验证通过！");
        }
        return result;
    }

    @GetMapping("/NotIdentifyTest")
    @Operation(summary="无需验证测试接口", description="")
    public Result notIdentifyTest(){
        return ResultGenerator.genSuccessResult("无需登陆验证");
    }
}

package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="管理员接口", description = "")
@RequestMapping("/api/v1")
public class AdminController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserTokenMapper userTokenMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    StoreReviewMapper storeReviewMapper;
    @Autowired
    OrderMapper orderMapper;

    @PutMapping("/admin/store/{storeId}")
    @Operation(summary = "修改店铺信息",description = "")
    public Result<String> adminStore(@PathVariable("storeId")String storeId,
                                     @RequestBody Store storeParam,
                                     @TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        if(!user.getIsAdmin()){
            return ResultGenerator.genFailResult("NOT ADMIN");
        }
        if(!storeParam.getStoreId().equals(storeId)){
            return ResultGenerator.genFailResult("INCORRECT ID");
        }
        if(storeMapper.updateByStoreId(storeParam)>0){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("EDIT FAILED");
    }

    @PutMapping("/admin/product/{productId}")
    @Operation(summary = "修改商品信息",description = "")
    public Result<String> adminProduct(@PathVariable("productId")String productId,
                                       @RequestBody Product productParam,
                                       @TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        if(!user.getIsAdmin()){
            return ResultGenerator.genFailResult("NOT ADMIN");
        }
        if(!productParam.getProductID().equals(productId)){
            return ResultGenerator.genFailResult("INCORRECT ID");
        }
        if(productMapper.updateByProductId(productParam)>0){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("EDIT FAILED");
    }

    @PutMapping("/admin/store_review/{storeReviewId}")
    @Operation(summary = "修改店铺评论信息",description = "")
    public Result<String> adminStoreReview(@PathVariable("storeReviewId")String storeReviewId,
                                       @RequestBody StoreReview storeReviewParam,
                                       @TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        if(!user.getIsAdmin()){
            return ResultGenerator.genFailResult("NOT ADMIN");
        }
        if(!storeReviewParam.getReviewID().equals(storeReviewId)){
            return ResultGenerator.genFailResult("INCORRECT ID");
        }
        if(storeReviewMapper.updateByReviewId(storeReviewParam)>0){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("EDIT FAILED");
    }

    @PutMapping("/admin/user/{userId}")
    @Operation(summary = "修改用户信息",description = "")
    public Result<String> adminUser(@PathVariable("userId")String userId,
                                    @RequestBody User userParam,
                                    @TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        if(!user.getIsAdmin()){
            return ResultGenerator.genFailResult("NOT ADMIN");
        }
        if(!userParam.getUserId().equals(userId)){
            return ResultGenerator.genFailResult("INCORRECT ID");
        }
        if(userMapper.updateByUserId(userParam)>0){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("EDIT FAILED");
    }

    @PutMapping("/admin/order/{orderId}")
    @Operation(summary = "修改订单信息",description = "")
    public Result<String> adminUser(@PathVariable("orderId")String orderId,
                                    @RequestBody Order orderParam,
                                    @TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        if(!user.getIsAdmin()){
            return ResultGenerator.genFailResult("NOT ADMIN");
        }
        if(!orderParam.getOrderId().equals(orderId)){
            return ResultGenerator.genFailResult("INCORRECT ID");
        }
        if(orderMapper.updateByOrderId(orderParam)>0){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("EDIT FAILED");
    }
}

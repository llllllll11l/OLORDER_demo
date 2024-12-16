package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.controller.Param.StoreReviewAddParam;
import com.example.demo.controller.vo.StoreListVO;
import com.example.demo.controller.vo.StoreReviewListVO;
import com.example.demo.dao.*;
import com.example.demo.entity.Order;
import com.example.demo.entity.Store;
import com.example.demo.entity.StoreReview;
import com.example.demo.entity.User;
import com.example.demo.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name="评论接口", description = "")
@RequestMapping("/api/v1")
public class ReviewController {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    StoreReviewMapper storeReviewMapper;
    @Resource
    private ReviewService reviewService;

    @GetMapping("/store/{storeId}/reviews")
    @Operation(summary = "查看店铺的评论",description = "")
    public Result<List<StoreReviewListVO>> showReviewsByStore(@PathVariable("storeId")String storeId,
                                                             @TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        Store store=storeMapper.selectByStoreId(storeId);
        List<StoreReviewListVO> storeReviewListVOList=new ArrayList<>();
        List<StoreReview> storeReviewList=storeReviewMapper.selectByStoreId(storeId);
        if(storeReviewList==null){
            return ResultGenerator.genFailResult("REVIEW NOT FOUND");
        }
        for(StoreReview i:storeReviewList){
            StoreReviewListVO t=new StoreReviewListVO();
            BeanUtils.copyProperties(i,t);
            storeReviewListVOList.add(t);
        }
        return ResultGenerator.genSuccessResult(storeReviewListVOList);
    }

    @PostMapping("/store/{storeId}/add_review")
    @Operation(summary = "添加店铺评论",description = "")
    public Result<String> addStoreReview(@PathVariable("storeId")String storeId,
                                         @RequestBody @Valid StoreReviewAddParam storeReviewAddParam,
                                         @TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        Store store=storeMapper.selectByStoreId(storeId);
        if(store==null){
            return ResultGenerator.genFailResult("STORE NOT FOUND");
        }
        List<Order> orderList=orderMapper.selectByUserIdAndStoreId(user.getUserId(),storeId);
        if(orderList==null){
            return ResultGenerator.genFailResult("NO ORDER");
        }
        String result=reviewService.addStoreReview(user.getUserId(),storeId,storeReviewAddParam);
        if(result==null){
            return ResultGenerator.genFailResult("ADD REVIEW FAILED");
        }
        return ResultGenerator.genSuccessResult(result);
    }
}

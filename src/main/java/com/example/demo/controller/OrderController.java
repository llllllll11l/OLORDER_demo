package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.controller.vo.OrderVO;
import com.example.demo.dao.OrderMapper;
import com.example.demo.dao.ProductMapper;
import com.example.demo.dao.StoreMapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.Store;
import com.example.demo.entity.User;
import com.example.demo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="订单接口",description = "")
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    UserMapper userMapper;
    @Resource
    private OrderService orderService;

    @GetMapping("/order/{orderId}")
    @Operation(summary = "查看订单",description = "")
    public Result<OrderVO> getOrderDetail(@PathVariable("orderId")String orderId,
                                          @TokenRequired User user){
        Order order=orderMapper.selectByOrderId(orderId);
        if(order==null||user==null){
            return ResultGenerator.genFailResult("FAILED");
        }
        OrderVO orderVO=new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        return ResultGenerator.genSuccessResult(orderVO);
    }

    @GetMapping("/order/user")
    @Operation(summary = "查看客户参与的订单",description = "")
    public Result<List<Order>> getOrderByUser(@TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        List<Order> orderList=orderMapper.selectByUserId(user.getUserId());
        return ResultGenerator.genSuccessResult(orderList);
    }

    @GetMapping("/order/store/{storeId}")
    @Operation(summary = "查看店铺的订单",description = "")
    public Result<List<Order>> getOrderByStore(@PathVariable("storeId")String storeId,
                                               @TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        List<Store> storeList=storeMapper.selectByUserId(user.getUserId());
        if(!storeList.contains(storeMapper.selectByStoreId(storeId))){
            return ResultGenerator.genFailResult("NO PERMISSION");
        }
        List<Order> orderList=orderMapper.selectByStoreId(storeId);
        return ResultGenerator.genSuccessResult(orderList);
    }
}

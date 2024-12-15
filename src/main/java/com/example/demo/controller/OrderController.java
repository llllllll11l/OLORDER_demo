package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.UserStatus;
import com.example.demo.Enums.UserType;
import com.example.demo.Util.Pair;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.controller.vo.OrderVO;
import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    OrderItemMapper orderItemMapper;
    @Resource
    private OrderService orderService;

    @GetMapping("/order/{orderId}")
    @Operation(summary = "查看订单",description = "")
    public Result<OrderVO> getOrderDetail(@PathVariable("orderId")String orderId,
                                          @TokenRequired User user){
        Order order=orderMapper.selectByOrderId(orderId);
        if(order==null||user==null){
            return ResultGenerator.genFailResult("NOT FOUND");
        }
        if(user.getStatus()!=UserStatus.ACTIVE){
            return ResultGenerator.genFailResult("USER STATUS ERROR");
        }
        OrderVO orderVO=new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        orderVO.setItems(orderItemMapper.selectByOrderId(orderId));
        return ResultGenerator.genSuccessResult(orderVO);
    }

    @GetMapping("/order/user")
    @Operation(summary = "查看客户参与的订单",description = "")
    public Result<List<Order>> getOrderByUser(@TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        if(user.getStatus()!=UserStatus.ACTIVE){
            return ResultGenerator.genFailResult("USER STATUS ERROR");
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
        if(user.getUserType()!= UserType.MERCHANT){
            return ResultGenerator.genFailResult("NO PERMISSION");
        }
        List<Store> storeList=storeMapper.selectByUserId(user.getUserId());
        if(!storeList.contains(storeMapper.selectByStoreId(storeId))){
            return ResultGenerator.genFailResult("NO PERMISSION");
        }
        List<Order> orderList=orderMapper.selectByStoreId(storeId);
        return ResultGenerator.genSuccessResult(orderList);
    }

    @PutMapping("/order/customer/{orderId}")
    @Operation(summary = "客户确认订单",description = "order status:PENDING=>PAID")
    public Result<String> customerConfirmOrder(@PathVariable("orderId")String orderId,
                                               @TokenRequired User user){
        if(user.getStatus()!=UserStatus.ACTIVE){
            return ResultGenerator.genFailResult("USER STATUS ERROR");
        }
        if(user.getUserType()!=UserType.CUSTOMER){
            return ResultGenerator.genFailResult("NO PERMISSION");
        }
        Order order=orderMapper.selectByOrderId(orderId);
        if(order==null){
            return ResultGenerator.genSuccessResult("ORDER NOT FOUND");
        }
        order.setOrderStatus(OrderStatus.PAID);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/store/merchant/{orderId}/")
    @Operation(summary = "商家确认订单",description = "order status:PAID=>CONFIRMED")
    public Result<String> merchantConfirmOrder(@PathVariable("orderId")String orderId,
                                               @TokenRequired User user){
        if(user.getStatus()!=UserStatus.ACTIVE){
            return ResultGenerator.genFailResult("USER STATUS ERROR");
        }
        if(user.getUserType()!=UserType.MERCHANT){
            return ResultGenerator.genFailResult("NO PERMISSION");
        }
        Order order=orderMapper.selectByOrderId(orderId);
        if(order==null){
            return ResultGenerator.genSuccessResult("ORDER NOT FOUND");
        }
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/store/{storeId}/products/{productId}/cart/add")
    @Operation(summary = "加入购物车",description = "加一个，如果没有订单则创建一个")
    public Result<Pair<String,String>> addToCartBy1(@PathVariable("storeId")String storeId,
                                                    @PathVariable("productId")String productId,
                                                    @TokenRequired User user){
        Store store=storeMapper.selectByStoreId(storeId);
        if(store==null){
            return ResultGenerator.genFailResult("STORE NOT FOUND");
        }
        Product product=productMapper.selectByProductId(productId);
        if(product==null){
            return ResultGenerator.genFailResult("PRODUCT NOT FOUND");
        }
        Pair<String,String>result=orderService.addToCartBy1(user.getUserId(),storeId,productId);
        if (result == null) {
            return ResultGenerator.genFailResult("ADD FAILED");
        }
        return ResultGenerator.genSuccessResult(result);
    }


}
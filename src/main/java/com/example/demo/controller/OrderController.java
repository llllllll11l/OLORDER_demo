package com.example.demo.controller;

import com.example.demo.Config.TokenRequired;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Enums.UserStatus;
import com.example.demo.Enums.UserType;
import com.example.demo.Util.Pair;
import com.example.demo.Util.Result;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.controller.Param.OrderCustomerConfirmParam;
import com.example.demo.controller.Param.OrderMerchantConfirmParam;
import com.example.demo.controller.vo.OrderVO;
import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
                                               @RequestBody @Valid OrderCustomerConfirmParam orderCustomerConfirmParam,
                                               @TokenRequired User user){
        if(user.getStatus()!=UserStatus.ACTIVE){
            return ResultGenerator.genFailResult("USER STATUS ERROR");
        }
        if(user.getUserType()!=UserType.CUSTOMER){
            return ResultGenerator.genFailResult("NO PERMISSION");
        }
        Order order=orderMapper.selectByOrderId(orderId);
        if(order==null){
            return ResultGenerator.genFailResult("ORDER NOT FOUND");
        }
        if(orderCustomerConfirmParam.isConfirm()){
            order.setOrderStatus(OrderStatus.PAID);
        }
        else{
            order.setOrderStatus(OrderStatus.CANCELED);
        }
        order.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        order.setDeliveryAddress(orderCustomerConfirmParam.getDeliveryAddress());
        if(orderMapper.updateByOrderId(order)>0)
            return ResultGenerator.genSuccessResult();
        return ResultGenerator.genFailResult("UPDATE ORDER STATUS FAILED");
    }

    @PutMapping("/store/merchant/{orderId}/")
    @Operation(summary = "商家确认订单",description = "order status:PAID=>CONFIRMED")
    public Result<String> merchantConfirmOrder(@PathVariable("orderId")String orderId,
                                               @RequestBody @Valid OrderMerchantConfirmParam orderMerchantConfirmParam,
                                               @TokenRequired User user){
        if(user.getStatus()!=UserStatus.ACTIVE){
            return ResultGenerator.genFailResult("USER STATUS ERROR");
        }
        if(user.getUserType()!=UserType.MERCHANT){
            return ResultGenerator.genFailResult("NO PERMISSION");
        }
        Order order=orderMapper.selectByOrderId(orderId);
        if(order==null){
            return ResultGenerator.genFailResult("ORDER NOT FOUND");
        }
        if(order.getOrderStatus()!=OrderStatus.PAID){
            return ResultGenerator.genFailResult("ORDER STATUS ERROR");
        }
        if(orderMerchantConfirmParam.isConfirm()) {
            System.out.println("CONFIRMING!!");
            order.setOrderStatus(OrderStatus.CONFIRMED);
        }
        else{
            order.setOrderStatus(OrderStatus.CANCELED);
        }
        order.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        if(orderMapper.updateByOrderId(order)>0)
            return ResultGenerator.genSuccessResult();
        return ResultGenerator.genFailResult("UPDATE ORDER STATUS FAILED");
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
            return ResultGenerator.genFailResult(ServiceResultEnum.ADD_TO_CART_FAILED.getResult());
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @PutMapping("/store/{storeId}/products/{productId}/cart/remove")
    @Operation(summary = "从购物车移除",description = "")
    public Result<Pair<String,String>> removeFromCart(@PathVariable("storeId")String storeId,
                                         @PathVariable("productId")String productId,
                                         @TokenRequired User user){
        if(user==null){
            return ResultGenerator.genFailResult("USER NOT FOUND");
        }
        Store store=storeMapper.selectByStoreId(storeId);
        if(store==null){
            return ResultGenerator.genFailResult("STORE NOT FOUND");
        }
        Product product=productMapper.selectByProductId(productId);
        if(product==null){
            return ResultGenerator.genFailResult("PRODUCT NOT FOUND");
        }
        String result=orderService.removeFromCart(user.getUserId(),storeId,productId);
        if(result==null){
            return ResultGenerator.genFailResult(ServiceResultEnum.REMOVE_FROM_CART_FAILED.getResult());
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @GetMapping("/store/{storeId}/cart")
    @Operation(summary = "查看购物车", description = "")
    public Result<OrderVO> showCart(@PathVariable("storeId")String storeId,
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
            return ResultGenerator.genFailResult("ORDER NOT FOUND");
        }
        Order order=null;
        for(Order i:orderList){
            if(i.getOrderStatus()== OrderStatus.PENDING){
                order=i;
                break;
            }
        }
        if(order==null){
            return ResultGenerator.genFailResult("ORDER NOT FOUND");
        }
        OrderVO orderVO=new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        orderVO.setItems(orderItemMapper.selectByOrderId(order.getOrderId()));
        return ResultGenerator.genSuccessResult(orderVO);
    }
}

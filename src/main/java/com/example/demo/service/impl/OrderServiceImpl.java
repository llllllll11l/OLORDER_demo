package com.example.demo.service.impl;

import com.example.demo.Enums.OrderStatus;
import com.example.demo.Util.Pair;
import com.example.demo.Util.ResultGenerator;
import com.example.demo.dao.*;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public String createOrder(String userId, String storeId) {
        Order order=new Order();
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setCreateAt(new Timestamp(System.currentTimeMillis()));
        order.setTotalAmount(new BigDecimal(0));
        order.setOrderID(UUID.randomUUID().toString());
        order.setStoreId(storeId);
        order.setCustomerId(userId);
        order.setOrderStatus(OrderStatus.PENDING);
        if(orderMapper.insertSelective(order)>0)
            return order.getOrderID();
        return "FAILED";
    }

    @Override
    public String createOrderItem(String orderId, String productId) {
        OrderItem orderItem=new OrderItem();
        orderItem.setTotalPrice(new BigDecimal(0));
        orderItem.setQuantity(0);
        orderItem.setOrderId(orderId);
        orderItem.setProductId(productId);
        orderItem.setPrice(productMapper.selectByProductId(productId).getPrice());
        orderItem.setOrderId(UUID.randomUUID().toString());
        if(orderItemMapper.insertSelective(orderItem)>0)
            return orderItem.getOrderItemId();
        return "FAILED";
    }

    @Override
    public Pair<String, String> addToCartBy1(String userId, String storeId, String productId) {
        Product product=productMapper.selectByProductId(productId);

        List<Order> orderList=orderMapper.selectByUserIdAndStoreId(userId,storeId);
        //flag==true -- 已有PENDING订单
        boolean flag=false;
        Order order=null;
        for(Order i:orderList){
            if(i.getOrderStatus()== OrderStatus.PENDING){
                flag=true;
                order=i;
                break;
            }
        }
        if(!flag){
            String createOrderResult =createOrder(userId,storeId);
            if(createOrderResult.equals("FAILED")){
                return null;
            }
            order=orderMapper.selectByOrderId(createOrderResult);
        }
        order.setTotalAmount(order.getTotalAmount().add(product.getPrice()));
        OrderItem orderItem=orderItemMapper.selectByOrderIdAndProductId(order.getOrderID(),productId);
        if(orderItem==null){
            String createOrderItemResult =createOrderItem(order.getOrderID(),productId);
            if(createOrderItemResult.equals("FAILED")){
                return null;
            }
            orderItem=orderItemMapper.selectByOrderItemId(createOrderItemResult);
        }
        orderItem.setQuantity(orderItem.getQuantity()+1);
        BigDecimal quant=new BigDecimal(orderItem.getQuantity());
        orderItem.setTotalPrice(orderItem.getPrice().multiply(quant));
        return new Pair<>(order.getOrderID(), orderItem.getOrderItemId());
    }

    @Override
    public Pair<String, String> removeFromCart(String userId, String storeId, String productId) {
        return null;
    }
}

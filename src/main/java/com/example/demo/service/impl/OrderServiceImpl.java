package com.example.demo.service.impl;

import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Util.Pair;
import com.example.demo.dao.*;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
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
        order.setOrderId(UUID.randomUUID().toString());
        order.setStoreId(storeId);
        order.setCustomerId(userId);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setDeliveryAddress("PENDING");

        Store store=storeMapper.selectByStoreId(storeId);
        store.setVisited(store.getVisited()+1);
        storeMapper.updateByStoreId(store);

        if(orderMapper.insertSelective(order)>0)
            return order.getOrderId();
        return ServiceResultEnum.CREATE_ORDER_FAILED.getResult();
    }

    @Override
    public String createOrderItem(String orderId, String productId) {
        OrderItem orderItem=new OrderItem();
        orderItem.setTotalPrice(new BigDecimal(0));
        orderItem.setQuantity(0);
        orderItem.setOrderId(orderId);
        orderItem.setProductId(productId);
        orderItem.setPrice(productMapper.selectByProductId(productId).getPrice());
        orderItem.setOrderItemId(UUID.randomUUID().toString());
        if(orderItemMapper.insertSelective(orderItem)>0)
            return orderItem.getOrderItemId();
        return ServiceResultEnum.CREATE_ORDER_ITEM_FAILED.getResult();
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
            if(createOrderResult.equals(ServiceResultEnum.CREATE_ORDER_FAILED.getResult())){
                return null;
            }
            order=orderMapper.selectByOrderId(createOrderResult);
        }
        OrderItem orderItem=orderItemMapper.selectByOrderIdAndProductId(order.getOrderId(),productId);
        if(orderItem==null){
            String createOrderItemResult =createOrderItem(order.getOrderId(),productId);
            if(createOrderItemResult.equals(ServiceResultEnum.CREATE_ORDER_ITEM_FAILED.getResult())){
                return null;
            }
            orderItem=orderItemMapper.selectByOrderItemId(createOrderItemResult);
        }
        order.setTotalAmount(order.getTotalAmount().add(product.getPrice()));
        System.out.println("-----"+order.getTotalAmount());
        order.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        orderItem.setQuantity(orderItem.getQuantity()+1);
        System.out.println("-----"+orderItem.getQuantity());
        orderItem.setTotalPrice(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
        System.out.println("-----"+orderItem.getTotalPrice());
        if(orderMapper.updateByOrderId(order)<=0||orderItemMapper.updateByOrderItemId(orderItem)<=0)
            return null;
        return new Pair<>(order.getOrderId(), orderItem.getOrderItemId());
    }

    @Override
    public String removeFromCart(String userId, String storeId, String productId) {
        Product product=productMapper.selectByProductId(productId);
        List<Order> orderList=orderMapper.selectByUserIdAndStoreId(userId,storeId);
        if(orderList==null){
            return null;
        }
        Order order=null;
        for(Order i:orderList){
            if(i.getOrderStatus()== OrderStatus.PENDING){
                order=i;
                break;
            }
        }
        if(order==null){
            return null;
        }
        OrderItem orderItem=orderItemMapper.selectByOrderIdAndProductId(order.getOrderId(),productId);
        if(orderItemMapper.deleteByOrderItemId(orderItem.getOrderItemId())>0){
            return order.getOrderId();
        }
        return null;
    }
}

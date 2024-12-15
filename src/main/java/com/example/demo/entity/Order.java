package com.example.demo.entity;

import com.example.demo.Enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Order {
    private String orderID;
    private String customerId;
    private String storeId;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private Timestamp orderDate;
    private Timestamp createAt;
    private Timestamp updateAt;
}

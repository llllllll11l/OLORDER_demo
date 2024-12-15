package com.example.demo.controller.vo;

import com.example.demo.Enums.OrderStatus;
import com.example.demo.entity.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderVO implements Serializable {
    private String orderID;
    private String customerId;
    private String storeId;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private String deliveryAddress;
    private Timestamp orderDate;
    private Timestamp createAt;
    private Timestamp updateAt;
    private List<OrderItem> items;
}

package com.example.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

@Data
public class Orders {
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

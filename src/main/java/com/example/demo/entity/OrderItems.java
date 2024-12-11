package com.example.demo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

@Data
public class OrderItems {
    private String orderItemsId;
    private String orderId;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}

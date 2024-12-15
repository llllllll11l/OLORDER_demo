package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private String orderItemId;
    private String orderId;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}

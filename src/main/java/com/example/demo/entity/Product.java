package com.example.demo.entity;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.example.demo.Enums.ProductStatus;
import lombok.Data;

@Data
public class Product {
    private String productID;
    private String productName;
    private String productDescription;
    private String productCategory;
    private BigDecimal price;
    private int stockQuantity;
    private ProductStatus productStatus;
    private String productImage;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String storeID;
}

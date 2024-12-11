package com.example.demo.entity;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
public class Porducts {
    private String productID;
    private String productName;
    private String productDescription;
    private String productCategory;
    private BigDecimal price;
    private int stockQuantity;
    private ProductStatus productStatus;
    private Timestamp productImage;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String storeID;
}

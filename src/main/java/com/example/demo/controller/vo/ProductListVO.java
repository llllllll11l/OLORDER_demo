package com.example.demo.controller.vo;

import com.example.demo.Enums.ProductStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ProductListVO implements Serializable {
    private String productID;
    private String productName;
    private String productDescription;
    private String productCategory;
    private BigDecimal price;
    private String productImage;
}

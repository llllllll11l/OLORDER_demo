package com.example.demo.controller.Param;

import com.example.demo.Enums.ProductStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductUpdateParam implements Serializable {
    private String productName;
    private String productDescription;
    private String productCategory;
    private BigDecimal price;
    private int stockQuantity;
    private ProductStatus productStatus;
    private String productImage;

    public ProductUpdateParam(){}
}

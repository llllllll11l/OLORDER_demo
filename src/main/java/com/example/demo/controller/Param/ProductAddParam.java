package com.example.demo.controller.Param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductAddParam implements Serializable {
    @NotEmpty(message = "商品名不能为空")
    private String productName;
    private String productDescription;
    @Schema(description = "商品类别")
    private String productCategory;
    @NotEmpty(message = "商品单价不能为空")
    private BigDecimal price;
    private int stockQuantity;
    private String productImage;
    private String storeId;

    public ProductAddParam(){}
}

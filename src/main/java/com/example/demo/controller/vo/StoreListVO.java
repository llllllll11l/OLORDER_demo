package com.example.demo.controller.vo;

import com.example.demo.Enums.StoreType;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class StoreListVO {
    private String storeName;
    private String storeDescription;
    private String contactNumber;
    private StoreType storeType;
    /*
    @Parameter(description = "近期平均评分")
    private double rating;
    @Parameter(description = "近期访问量")
    private int visited;
    */
}

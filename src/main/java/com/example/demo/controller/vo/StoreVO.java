package com.example.demo.controller.vo;

import com.example.demo.Enums.StoreType;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import java.io.Serializable;

@Data
public class StoreVO implements Serializable {
    private String storeId;
    private String storeName;
    private String storeDescription;
    private String storeAddress;
    private String contactNumber;
    private StoreType storeType;
    private double rating;
    private int visited;
}

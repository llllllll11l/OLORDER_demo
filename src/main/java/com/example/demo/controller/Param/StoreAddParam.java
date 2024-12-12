package com.example.demo.controller.Param;

import com.example.demo.entity.StoreType;
import lombok.Data;

@Data
public class StoreAddParam {
    private String storeName;
    private String storeDescription;
    private String storeAddress;
    private String contactNumber;
    private StoreType storeType;
}

package com.example.demo.controller.Param;

import com.example.demo.entity.StoreType;
import lombok.Data;

@Data
public class StoreUpdateInfoParam {
    private String storeName;
    private String storeAddress;
    private String storeDescription;
    private StoreType storeType;
    private String contactNumber;
}

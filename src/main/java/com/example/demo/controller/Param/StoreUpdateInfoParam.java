package com.example.demo.controller.Param;

import com.example.demo.Enums.StoreType;
import lombok.Data;

import java.io.Serializable;

@Data
public class StoreUpdateInfoParam implements Serializable {
    private String storeName;
    private String storeAddress;
    private String storeDescription;
    private StoreType storeType;
    private String contactNumber;

    public StoreUpdateInfoParam(){}
}

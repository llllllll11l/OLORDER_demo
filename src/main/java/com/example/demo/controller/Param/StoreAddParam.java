package com.example.demo.controller.Param;

import com.example.demo.Enums.StoreType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StoreAddParam implements Serializable {
    @NotEmpty(message = "店铺名不能为空")
    private String storeName;
    private String storeDescription;
    @NotEmpty(message = "店铺地址不能为空")
    private String storeAddress;
    @NotEmpty(message = "联系电话不能为空")
    private String contactNumber;
    private StoreType storeType;

    public StoreAddParam(){}
}

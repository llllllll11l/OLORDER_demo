package com.example.demo.service.impl;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.controller.Param.StoreUpdateInfoParam;
import com.example.demo.dao.ProductMapper;
import com.example.demo.dao.StoreMapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public ServiceResultEnum addStore(Store store) {
        return null;
    }

    @Override
    public ServiceResultEnum updateInfo(StoreUpdateInfoParam storeUpdateInfoParam, String storeId) {
        return null;
    }

    @Override
    public ServiceResultEnum updateProduct(Product product, String storeId) {
        return null;
    }

    @Override
    public ServiceResultEnum addProduct(Product product, String storeId) {
        return null;
    }

    @Override
    public ServiceResultEnum addVerification(String verificationDocs, String storeId) {
        return null;
    }
}
package com.example.demo.service.impl;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Enums.StoreStatus;
import com.example.demo.controller.Param.ProductAddParam;
import com.example.demo.controller.Param.ProductUpdateParam;
import com.example.demo.controller.Param.StoreAddParam;
import com.example.demo.controller.Param.StoreUpdateInfoParam;
import com.example.demo.dao.ProductMapper;
import com.example.demo.dao.StoreMapper;
import com.example.demo.dao.UserMapper;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public String addStore(StoreAddParam storeAddParam,String userId) {
        Store store=new Store();
        store.setStoreStatus(StoreStatus.PENDING);
        store.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        store.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        store.setStoreId(UUID.randomUUID().toString());
        store.setOwnerID(userId);
        BeanUtils.copyProperties(storeAddParam,store);
        if(storeMapper.insertSelective(store)>0){
            return store.getStoreId();
        }
        else{
            return "ADD_STORE_FAILED";
        }
    }

    @Override
    public ServiceResultEnum updateInfo(StoreUpdateInfoParam storeUpdateInfoParam,String storeId) {
        Store store=storeMapper.selectByStoreId(storeId);
        if(store==null){
            return ServiceResultEnum.STORE_NOT_FOUND;
        }
        BeanUtils.copyProperties(storeUpdateInfoParam,store);
        store.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        if(storeMapper.updateByStoreId(store)>0){
            return ServiceResultEnum.SUCCESS;
        }
        else{
            return ServiceResultEnum.ADD_STORE_FAILED;
        }
    }

    @Override
    public ServiceResultEnum delStore(String storeId) {
        if(storeMapper.deleteByStoreId(storeId)>0)
            return ServiceResultEnum.SUCCESS;
        return ServiceResultEnum.DELETE_STORE_FAILED;
    }

    @Override
    public ServiceResultEnum updateProduct(ProductUpdateParam productUpdateParam,String storeId,String productId) {
        Store store=storeMapper.selectByStoreId(storeId);
        Product product=productMapper.selectByProductId(productId);
        if(store==null)
            return ServiceResultEnum.STORE_NOT_FOUND;
        if(product==null)
            return ServiceResultEnum.PRODUCT_NOT_FOUND;
        List<Product> productListOfStore=productMapper.selectByStoreId(storeId);
        if(!productListOfStore.contains(product)){
            return ServiceResultEnum.UPDATE_FAILED;
        }
        BeanUtils.copyProperties(productUpdateParam,product);
        product.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        if(productMapper.updateByProductId(product)>0){
            return ServiceResultEnum.SUCCESS;
        }
        else{
            return ServiceResultEnum.UPDATE_FAILED;
        }
    }

    @Override
    public String addProduct(ProductAddParam productAddParam,String storeId) {
        Product product=new Product();
        product.setProductID(UUID.randomUUID().toString());
        product.setCreateDate(new Timestamp(System.currentTimeMillis()));
        product.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        product.setStoreID(storeId);
        BeanUtils.copyProperties(productAddParam,product);
        if(productMapper.insertSelective(product)>0){
            return product.getProductID();
        }
        else{
            return "ADD_PRODUCT_FAILED";
        }
    }

    @Override
    public ServiceResultEnum delProduct(String productId) {
        return null;
    }

    @Override
    public ServiceResultEnum addVerification(String verificationDocs, String storeId) {

        return null;
    }
}

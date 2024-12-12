package com.example.demo.dao;

import com.example.demo.entity.Store;

import java.util.List;

public interface StoreMapper {
    Store selectByUserId(String userId);
    Store selectByStoreId(String storeId);
    int insertSelective(Store store);
    int updateByStoreId(Store store);
    int deleteByStoreId(String storeId);//先不用实现
    List<Store> findAllStores();//调试用？
}

package com.example.demo.service;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.entity.Store;

public interface StoreService {
    /**
     * 添加店铺
     *
     * @param store
     * @return
     */
    ServiceResultEnum addStore(Store store);

    /**
     * 修改店铺信息
     *
     * @param storeUpdateInfoParam
     * @param storeId
     * @return
     */
    ServiceResultEnum updateInfo(StoreUpdateInfoParam storeUpdateInfoParam, String storeId);
}

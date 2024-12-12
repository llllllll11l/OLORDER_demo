package com.example.demo.service;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.controller.Param.StoreUpdateInfoParam;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;

import java.util.List;

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

    /**
     * 更新店铺商品
     *
     * @param product
     * @param storeId
     * @return
     */
    ServiceResultEnum updateProduct(Product product, String storeId);

    /**
     * 添加店铺商品
     *
     * @param product
     * @param storeId
     * @return
     */
    ServiceResultEnum addProduct(Product product, String storeId);

    /**
     * 上传店铺凭证文件（URL）
     *
     * @param verificationDocs
     * @param storeId
     * @return
     */
    ServiceResultEnum addVerification(String verificationDocs, String storeId);
}

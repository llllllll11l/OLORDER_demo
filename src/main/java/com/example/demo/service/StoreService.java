package com.example.demo.service;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.controller.Param.ProductAddParam;
import com.example.demo.controller.Param.ProductUpdateParam;
import com.example.demo.controller.Param.StoreAddParam;
import com.example.demo.controller.Param.StoreUpdateInfoParam;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;

import java.util.List;

public interface StoreService {
    /**
     * 添加店铺
     *
     * @param storeAddParam
     * @return
     */
    ServiceResultEnum addStore(StoreAddParam storeAddParam);

    /**
     * 修改店铺信息
     *
     * @param storeUpdateInfoParam
     * @return
     */
    ServiceResultEnum updateInfo(StoreUpdateInfoParam storeUpdateInfoParam);

    /**
     * 更新店铺商品
     *
     * @param productUpdateParam
     * @return
     */
    ServiceResultEnum updateProduct(ProductUpdateParam productUpdateParam);

    /**
     * 添加店铺商品
     *
     * @param productAddParam
     * @return
     */
    ServiceResultEnum addProduct(ProductAddParam productAddParam);

    /**
     * 上传店铺凭证文件（URL）
     *
     * @param verificationDocs
     * @param storeId
     * @return
     */
    ServiceResultEnum addVerification(String verificationDocs, String storeId);
}

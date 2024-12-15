package com.example.demo.service;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.controller.Param.ProductAddParam;
import com.example.demo.controller.Param.ProductUpdateParam;
import com.example.demo.controller.Param.StoreAddParam;
import com.example.demo.controller.Param.StoreUpdateInfoParam;

public interface StoreService {
    /**
     * 添加店铺
     *
     * @param storeAddParam
     * @return
     */
    ServiceResultEnum addStore(StoreAddParam storeAddParam,String userId);

    /**
     * 修改店铺信息
     *
     * @param storeUpdateInfoParam
     * @return
     */
    ServiceResultEnum updateInfo(StoreUpdateInfoParam storeUpdateInfoParam, String storeId);

    /**
     * delete the store by id
     *
     * @param storeId
     * @return
     */
    ServiceResultEnum delStore(String storeId);
    /**
     * 更新店铺商品
     *
     * @param productUpdateParam
     * @return
     */
    ServiceResultEnum updateProduct(ProductUpdateParam productUpdateParam,String storeId, String productId);

    /**
     * 添加店铺商品
     *
     * @param productAddParam
     * @return
     */
    ServiceResultEnum addProduct(ProductAddParam productAddParam, String storeId);

    /**
     * delete product by id
     *
     * @param productId
     * @return
     */
    ServiceResultEnum delProduct(String productId);
    /**
     * 上传店铺凭证文件（URL）
     *
     * @param verificationDocs
     * @param storeId
     * @return
     */

    ServiceResultEnum addVerification(String verificationDocs, String storeId);
}

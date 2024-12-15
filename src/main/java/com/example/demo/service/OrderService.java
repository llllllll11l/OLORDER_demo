package com.example.demo.service;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Util.Pair;

public interface OrderService {
    /**
     * 创建订单（购物车处）
     *
     * @param userId
     * @param storeId
     * @return
     */
    String createOrder(String userId, String storeId);

    /**
     * 创建订单物品
     *
     * @param orderId
     * @param productId
     * @return
     */
    String createOrderItem(String orderId, String productId);

    /**
     * 加入购物车(一次一个)
     *
     * @param userId
     * @param storeId
     * @param productId
     * @return
     */
    Pair<String,String> addToCartBy1(String userId, String storeId, String productId);

    /**
     * 从购物车移除(n个)
     *
     * @param userId
     * @param storeId
     * @param productId
     * @return
     */
    Pair<String,String> removeFromCart(String userId,String storeId, String productId);
}

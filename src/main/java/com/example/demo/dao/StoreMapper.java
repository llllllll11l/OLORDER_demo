package com.example.demo.dao;

import com.example.demo.Util.PageQuery;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;

import java.util.List;

public interface StoreMapper {
    List<Store> selectByUserId(String userId);
    Store selectByStoreId(String storeId);
    int insertSelective(Store store);
    int updateByStoreId(Store store);
    int deleteByStoreId(String storeId);//先不用实现
    List<Store> findAllStores();//调试用？

    /**
     * 根据搜索字段查询分页数据
     *
     * @param pageQuery
     * @return
     */
    List<Store> findStoreListBySearch(PageQuery pageQuery);

    int getNumOfStoresBySearch(PageQuery pageQuery);//暂不实现
}

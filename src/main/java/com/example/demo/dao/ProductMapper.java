package com.example.demo.dao;

import com.example.demo.entity.Product;

import java.util.List;

public interface ProductMapper {
    Product selectByProductId(String productId);
    List<Product> selectByStoreId(String storeId);
    int insertSelective(Product product);
    int deleteByProductId(String productId);
    int updateByProductId(Product product);
}

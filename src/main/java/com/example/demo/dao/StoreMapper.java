package com.example.demo.dao;

import com.example.demo.entity.Store;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StoreMapper {
    @Select("SELECT * FROM stores WHERE owner_id = #{userId}")
    Store selectByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM stores WHERE store_id = #{storeId}")
    Store selectByStoreId(@Param("storeId") String storeId);

    @Insert("<script>" +
            "INSERT INTO stores (" +
            "<if test='storeId != null'>store_id,</if>" +
            "<if test='storeName != null'>store_name,</if>" +
            "<if test='storeDescription != null'>store_description,</if>" +
            "<if test='storeAddress != null'>store_address,</if>" +
            "<if test='contactNumber != null'>contact_number,</if>" +
            "<if test='storeType != null'>store_type,</if>" +
            "<if test='storeStatus != null'>store_status,</if>" +
            "<if test='verificationDocs != null'>verification_docs,</if>" +
            "<if test='ownerID != null'>owner_id,</if>" +
            "<if test='createdAt != null'>created_at,</if>" +
            "<if test='updateAt != null'>update_at,</if>" +
            ") " +
            "VALUES (" +
            "<if test='storeId != null'>#{storeId},</if>" +
            "<if test='storeName != null'>#{storeName},</if>" +
            "<if test='storeDescription != null'>#{storeDescription},</if>" +
            "<if test='storeAddress != null'>#{storeAddress},</if>" +
            "<if test='contactNumber != null'>#{contactNumber},</if>" +
            "<if test='storeType != null'>#{storeType},</if>" +
            "<if test='storeStatus != null'>#{storeStatus},</if>" +
            "<if test='verificationDocs != null'>#{verificationDocs},</if>" +
            "<if test='ownerID != null'>#{ownerID},</if>" +
            "<if test='createdAt != null'>#{createdAt},</if>" +
            "<if test='updateAt != null'>#{updateAt},</if>" +
            ") " +
            "</script>")
    int insertSelective(Store store);

    @Update("<script>" +
            "UPDATE stores SET " +
            "<if test='storeName != null'>store_name = #{storeName},</if>" +
            "<if test='storeDescription != null'>store_description = #{storeDescription},</if>" +
            "<if test='storeAddress != null'>store_address = #{storeAddress},</if>" +
            "<if test='contactNumber != null'>contact_number = #{contactNumber},</if>" +
            "<if test='storeType != null'>store_type = #{storeType},</if>" +
            "<if test='storeStatus != null'>store_status = #{storeStatus},</if>" +
            "<if test='verificationDocs != null'>verification_docs = #{verificationDocs},</if>" +
            "<if test='ownerID != null'>owner_id = #{ownerID},</if>" +
            "<if test='createdAt != null'>created_at = #{createdAt},</if>" +
            "<if test='updateAt != null'>update_at = #{updateAt},</if>" +
            "WHERE store_id = #{storeId}" +
            "</script>")
    int updateByStoreId(Store store);


    int deleteByStoreId(String storeId);//先不用实现

    @Select("SELECT * FROM stores")
    List<Store> findAllStores();//调试用？
}

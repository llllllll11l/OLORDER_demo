package com.example.demo.dao;

import com.example.demo.entity.UserToken;
import org.apache.ibatis.annotations.*;

public interface UserTokenMapper {
    @Insert("<script>" +
            "INSERT INTO user_tokens (" +
            "<if test='tokenId != null'>token_id,</if>" +
            "<if test='userId != null'>user_id,</if>" +
            "<if test='token != null'>token,</if>" +
            "<if test='expireTime != null'>expire_time,</if>" +
            "<if test='updateTime != null'>update_time,</if>" +
            ") " +
            "VALUES (" +
            "<if test='tokenId != null'>#{tokenId},</if>" +
            "<if test='userId != null'>#{userId},</if>" +
            "<if test='token != null'>#{token},</if>" +
            "<if test='expireTime != null'>#{expireTime},</if>" +
            "<if test='updateTime != null'>#{updateTime},</if>" +
            ") " +
            "</script>")
    int insertSelective(UserToken record);

    @Select("SELECT * FROM user_tokens WHERE user_id = #{userId}")
    UserToken selectByUserId(@Param("userId") String userId);

    @Select("SELECT * FROM user_tokens WHERE token = #{token}")
    UserToken selectByToken(@Param("token") String token);

    @Update("<script>" +
            "UPDATE user_tokens SET " +
            "<if test='token != null'>token = #{token},</if>" +
            "<if test='expireTime != null'>expire_time = #{expireTime},</if>" +
            "<if test='updateTime != null'>update_time = #{updateTime},</if>" +
            "WHERE user_id = #{userId}" +
            "</script>")
    int updateByUserIdSelective(UserToken record);

    @Delete("DELETE FROM user_tokens WHERE user_id = #{userId}")
    int deleteByUserId(String userId);
}

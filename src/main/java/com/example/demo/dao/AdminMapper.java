package com.example.demo.dao;

import com.example.demo.entity.Admin;
import org.apache.ibatis.annotations.*;


import java.util.List;

public interface AdminMapper {
    @Select("SELECT * FROM admins WHERE admin_name = #{adminName} AND password_hash = #{passwordHash}")
    Admin selectByAdminNameAndPwd(@Param("adminName") String adminName,
                                 @Param("passwordHash") String passwordHash);

    @Select("SELECT * FROM admins WHERE admin_id = #{adminId}")
    Admin selectByAdminId(@Param("adminID") String adminId);

    @Update("<script>" +
            "UPDATE admins SET " +
            "<if test='adminName != null'>admin_name = #{adminName},</if>" +
            "<if test='passwordHash != null'>password_hash = #{passwordHash},</if>" +
            "<if test='email != null'>email = #{email},</if>" +
            "<if test='phoneNumber != null'>phone_number = #{phoneNumber},</if>" +
            "<if test='profilePicture != null'>profile_picture = #{profilePicture},</if>" +
            "<if test='registrationDate != null'>registration_date = #{registrationDate},</if>" +
            "<if test='lastLoginDate != null'>last_login_date = #{lastLoginDate},</if>" +
            "<if test='status != null'>status = #{status},</if>" +
            "<if test='lastPasswordChange != null'>last_password_change = #{lastPasswordChange},</if>" +
            "</set>" +
            "WHERE admin_id = #{adminID}" +
            "</script>")
    int updateByAdminId(Admin admin);

    @Insert("<script>" +
            "INSERT INTO admins " +
            "<trim prefix='(' suffix=')' suffixOverrides=','>" +
            "<if test='adminName != null'>admin_name,</if>" +
            "<if test='passwordHash != null'>password_hash,</if>" +
            "<if test='email != null'>email,</if>" +
            "<if test='phoneNumber != null'>phone_number,</if>" +
            "<if test='profilePicture != null'>profile_picture,</if>" +
            "<if test='registrationDate != null'>registration_date,</if>" +
            "<if test='lastLoginDate != null'>last_login_date,</if>" +
            "<if test='status != null'>status,</if>" +
            "<if test='lastPasswordChange != null'>last_password_change,</if>" +
            "</trim>" +
            "<trim prefix='VALUES (' suffix=')' suffixOverrides=','>" +
            "<if test='adminName != null'>#{adminName},</if>" +
            "<if test='passwordHash != null'>#{passwordHash},</if>" +
            "<if test='email != null'>#{email},</if>" +
            "<if test='phoneNumber != null'>#{phoneNumber},</if>" +
            "<if test='profilePicture != null'>#{profilePicture},</if>" +
            "<if test='registrationDate != null'>#{registrationDate},</if>" +
            "<if test='lastLoginDate != null'>#{lastLoginDate},</if>" +
            "<if test='status != null'>#{status},</if>" +
            "<if test='lastPasswordChange != null'>#{lastPasswordChange},</if>" +
            "</trim>" +
            "</script>")
    int insertSelective(Admin admin);

    @Select("SELECT * FROM admins")
    List<Admin> findAll();
}


package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username} AND password_hash = #{passwordHash}")
    User selectByUsernameAndPwd(@Param("username") String username,
                                @Param("passwordHash") String passwordHash);

    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    User selectByUserId(@Param("userId") String userId);

    @Update("<script>" +
            "UPDATE users SET " +
            "<if test='username != null'>username = #{username},</if>" +
            "<if test='passwordHash != null'>password_hash = #{passwordHash},</if>" +
            "<if test='email != null'>email = #{email},</if>" +
            "<if test='phoneNumber != null'>phone_number = #{phoneNumber},</if>" +
            "<if test='userType != null'>user_type = #{userType},</if>" +
            "<if test='profilePicture != null'>profile_picture = #{profilePicture},</if>" +
            "<if test='status != null'>status = #{status},</if>" +
            "<if test='lastPasswordChange != null'>last_password_change = #{lastPasswordChange},</if>" +
            "<if test='isEmailVerified != null'>is_email_verified = #{isEmailVerified},</if>" +
            "<if test='isPhoneVerified != null'>is_phone_verified = #{isPhoneVerified},</if>" +
            "WHERE user_id = #{userId}" +
            "</script>")
    int updateByUserId(User user);

    @Insert("<script>" +
            "INSERT INTO users (" +
            "<if test='userId != null'>user_id,</if>" +
            "<if test='username != null'>username,</if>" +
            "<if test='passwordHash != null'>password_hash,</if>" +
            "<if test='email != null'>email,</if>" +
            "<if test='phoneNumber != null'>phone_number,</if>" +
            "<if test='userType != null'>user_type,</if>" +
            "<if test='profilePicture != null'>profile_picture,</if>" +
            "<if test='registrationDate != null'>registration_date,</if>" +
            "<if test='status != null'>status,</if>" +
            "<if test='lastPasswordChange != null'>last_password_change,</if>" +
            "<if test='isEmailVerified != null'>is_email_verified,</if>" +
            "<if test='isPhoneVerified != null'>is_phone_verified,</if>" +
            ") " +
            "VALUES (" +
            "<if test='userId != null'>#{userId},</if>" +
            "<if test='username != null'>#{username},</if>" +
            "<if test='passwordHash != null'>#{passwordHash},</if>" +
            "<if test='email != null'>#{email},</if>" +
            "<if test='phoneNumber != null'>#{phoneNumber},</if>" +
            "<if test='userType != null'>#{userType},</if>" +
            "<if test='profilePicture != null'>#{profilePicture},</if>" +
            "<if test='registrationDate != null'>#{registrationDate},</if>" +
            "<if test='status != null'>#{status},</if>" +
            "<if test='lastPasswordChange != null'>#{lastPasswordChange},</if>" +
            "<if test='isEmailVerified != null'>#{isEmailVerified},</if>" +
            "<if test='isPhoneVerified != null'>#{isPhoneVerified},</if>" +
            ") " +
            "</script>")
    int insertSelective(User user);

    @Select("SELECT * FROM users")
    List<User> findAllUsers();
}

package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User selectByUsernameAndPwd(@Param("username") String username,
                                       @Param("passwordHash") String passwordHash);
    User selectByUserId(String userId);
    int updateByUserId(User user);
    int insertSelective(User user);
    List<User> findAllUsers();
}

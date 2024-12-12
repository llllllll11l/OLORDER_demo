package com.example.demo.dao;

import com.example.demo.entity.UserToken;

public interface UserTokenMapper {
    int insertSelective(UserToken record);
    UserToken selectByUserId(String userId);
    UserToken selectByToken(String token);
    int updateByUserIdSelective(UserToken record);
    int deleteByUserId(String userId);
}

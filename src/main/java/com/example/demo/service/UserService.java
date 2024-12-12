package com.example.demo.service;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.controller.Param.UserUpdateInfoParam;
import com.example.demo.Enums.UserType;

public interface UserService {
    /**
     * 登陆
     *
     * @param username
     * @param passwordHash
     * @return
     */
    ServiceResultEnum login(String username, String passwordHash);

    /**
     * 修改
     *
     * @param userUpdateInfo
     * @param userId
     * @return
     */
    ServiceResultEnum updateInfo(UserUpdateInfoParam userUpdateInfo, String userId);

    /**
     * 注销
     *
     * @param userId
     * @return
     */
    ServiceResultEnum logout(String userId);

    /**
     * 注册
     *
     * @param username
     * @param passwordHash
     * @param userType
     * @return
     */
    ServiceResultEnum register(String username, String passwordHash, UserType userType);
}

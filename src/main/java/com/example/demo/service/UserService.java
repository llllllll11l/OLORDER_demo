package com.example.demo.service;

import com.example.demo.Enums.ServiceResultEnum;

public interface UserService {
    /**
     * 登陆
     *
     * @param username
     * @param passwordHash
     * @return
     */
    ServiceResultEnum login(String username, String passwordHash);
}

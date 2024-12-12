package com.example.demo.service.impl;

import com.example.demo.Common.CustomException;
import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.Util.Util;
import com.example.demo.controller.Param.*;
import com.example.demo.dao.UserMapper;
import com.example.demo.dao.UserTokenMapper;
import com.example.demo.entity.User;
import com.example.demo.entity.UserToken;
import com.example.demo.entity.UserType;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

import static com.example.demo.Util.Util.generateToken;
import static com.example.demo.entity.UserStatus.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserMapper userMapper;
    @Autowired private UserTokenMapper userTokenMapper;

    @Override
    public ServiceResultEnum login(String username, String passwordHash){
        User user = userMapper.selectByUsernameAndPwd(username, passwordHash);
        long now=System.currentTimeMillis();
        Timestamp nowTimestamp=new Timestamp(now);
        Timestamp expireTimestamp=new Timestamp(now+2*24*3600*1000);
        if(user!=null){
            user.setLastLoginDate(nowTimestamp);
            if(user.getStatus()==DELETED)
                return ServiceResultEnum.USER_DELETED;
            String token=generateToken();
            UserToken userToken= userTokenMapper.selectByUserId(user.getUserId());
            if(userToken==null){
                userToken=new UserToken();
                userToken.setUserId(user.getUserId());
                userToken.setToken(token);
                userToken.setUpdateTime(nowTimestamp);
                userToken.setExpireTime(expireTimestamp);
                userToken.setTokenId(user.getUserId()+token);
                if(userTokenMapper.insertSelective(userToken)>0)
                    return ServiceResultEnum.LOGIN_SUCCESSED_NEW_TOKEN;
            }
            else{
                userToken.setToken(token);
                userToken.setUpdateTime(nowTimestamp);
                userToken.setExpireTime(expireTimestamp);
                userToken.setTokenId(user.getUserId()+token);
                if(userTokenMapper.updateByUserIdSelective(userToken)>0){
                    return ServiceResultEnum.LOGIN_SUCCESSED_UPDATE_TOKEN;
                }
            }
        }
        return ServiceResultEnum.LOGIN_ERROR;
    }

    @Override
    public ServiceResultEnum updateInfo(UserUpdateInfoParam userUpdateInfo, String userId) {
        User user = userMapper.selectByUserId(userId);
        System.out.println("--------"+userUpdateInfo.getEmail()+userUpdateInfo.getPasswordHash());
        if (user == null) {
            CustomException.fail(ServiceResultEnum.USER_NOT_FOUND.getResult());
            return ServiceResultEnum.UPDATE_FAILED;
        }
        if(userUpdateInfo.getUsername()!=null) {
            user.setUsername(userUpdateInfo.getUsername());
        }
        if(userUpdateInfo.getEmail()!=null){
            user.setIsEmailVerified(true);
            user.setEmail(userUpdateInfo.getEmail());
        }
        if(userUpdateInfo.getPhoneNumber()!=null){
            user.setIsPhoneVerified(true);
            user.setPhoneNumber(userUpdateInfo.getPhoneNumber());
        }
        if(userUpdateInfo.getProfilePicture()!=null){
            user.setProfilePicture(userUpdateInfo.getProfilePicture());
        }
        if (!Util.hashPassword("").equals(userUpdateInfo.getPasswordHash())){
            user.setPasswordHash(userUpdateInfo.getPasswordHash());
            Timestamp now=new Timestamp(System.currentTimeMillis());
            user.setLastPasswordChange(now);
        }
        if (userMapper.updateByUser(user)>0) {
            return ServiceResultEnum.SUCCESS;
        }
        else {
            return ServiceResultEnum.UPDATE_FAILED;
        }
    }

    @Override
    public ServiceResultEnum logout(String userId){
        if(userTokenMapper.deleteByUserId(userId)>0){
            return ServiceResultEnum.SUCCESS;
        }
        else{
            return ServiceResultEnum.LOGOUT_FAILED;
        }
    }
    @Override
    public ServiceResultEnum register(String username, String password, UserType userType){
        User reguser=new User();
        reguser.setUsername(username);
        reguser.setPasswordHash(Util.hashPassword(password));
        reguser.setUserType(userType);
        reguser.setIsEmailVerified(false);
        reguser.setIsPhoneVerified(false);
        reguser.setUserId(UUID.randomUUID().toString());
        java.sql.Timestamp regDate=new Timestamp(System.currentTimeMillis());
        reguser.setRegistrationDate(regDate);
        if(userMapper.insertSelective(reguser)>0){
            return ServiceResultEnum.SUCCESS;
        }
        else{
            return ServiceResultEnum.REGISTER_FAILED;
        }
    }
}

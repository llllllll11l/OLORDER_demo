package com.example.demo.service.impl;

import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.dao.UserMapper;
import com.example.demo.dao.UserTokenMapper;
import com.example.demo.entity.User;
import com.example.demo.Util.Util;
import com.example.demo.entity.UserToken;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserStatus;

import java.util.Date;

import static com.example.demo.Util.Util.generateToken;
import static com.example.demo.entity.UserStatus.DELETED;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserMapper userMapper;
    @Autowired private UserTokenMapper userTokenMapper;

    @Override
    public ServiceResultEnum login(String username, String passwordHash){
        User user = userMapper.selectByUsernameAndPwd(username, passwordHash);
        if(user!=null){
            if(user.getStatus()==DELETED)
                return ServiceResultEnum.USER_DELETED;
            String token=generateToken();
            UserToken userToken= userTokenMapper.selectByUserId(user.getUserId());
            Date now=new Date();
            Date expireTime=new Date(now.getTime()+2*24*3600*1000);
            if(userToken==null){
                userToken=new UserToken();
                userToken.setUserId(user.getUserId());
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);
                userToken.setTokenId(user.getUserId()+token);
                if(userTokenMapper.insertSelective(userToken)>0)
                    return ServiceResultEnum.LOGIN_SUCCESSED_NEW_TOKEN;
            }
            else{
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);
                userToken.setTokenId(user.getUserId()+token);
                if(userTokenMapper.updateByUserIdSelective(userToken)>0){
                    return ServiceResultEnum.LOGIN_SUCCESSED_UPDATE_TOKEN;
                }
            }
        }
        return ServiceResultEnum.LOGIN_ERROR;
    }
}

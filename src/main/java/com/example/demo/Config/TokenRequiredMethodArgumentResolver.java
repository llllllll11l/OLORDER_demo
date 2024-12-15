package com.example.demo.Config;

import com.example.demo.Common.CustomException;
import com.example.demo.Enums.ServiceResultEnum;
import com.example.demo.dao.UserMapper;
import com.example.demo.dao.UserTokenMapper;
import com.example.demo.entity.User;
import com.example.demo.Enums.UserStatus;
import com.example.demo.entity.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class TokenRequiredMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired private UserMapper userMapper;
    @Autowired private UserTokenMapper userTokenMapper;

    public TokenRequiredMethodArgumentResolver(){}

    public boolean supportsParameter(MethodParameter param){
        //System.out.println("Checking parameter: " + param.getParameterName());
        //System.out.println("Has TokenRequired annotation: " + param.hasMethodAnnotation(TokenRequired.class));
        return param.hasParameterAnnotation(TokenRequired.class);
    }

    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
        //System.out.println("Resolving parameter: " + param.getParameterName());
        //System.out.println("Parameter type: " + param.getParameterType());
        if(param.getParameterAnnotation(TokenRequired.class) != null){
            User user=null;
            String token=webRequest.getHeader("Token");
            if(token!=null&& !token.isEmpty()){
                UserToken userToken=userTokenMapper.selectByToken((token));
                if(userToken==null||userToken.getExpireTime().getTime()<=System.currentTimeMillis()){
                    CustomException.fail(ServiceResultEnum.TOKEN_EXPIRE_ERROR.getResult());
                }
                user=userMapper.selectByUserId(userToken.getUserId());
                if(user==null){
                    CustomException.fail(ServiceResultEnum.USER_NOT_FOUND.getResult());
                }
                if(user.getStatus()== UserStatus.SUSPENDED||user.getStatus()==UserStatus.DISABLED){
                    CustomException.fail(ServiceResultEnum.USER_DISABLED.getResult());
                }
                if(user.getStatus()==UserStatus.DELETED){
                    CustomException.fail(ServiceResultEnum.USER_DELETED.getResult());
                }
                return user;
            }
            else{
                CustomException.fail(ServiceResultEnum.USER_NOT_LOGGED_IN.getResult());
            }
        }
        return null;
    }
}

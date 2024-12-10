package com.example.demo.Enums;

public enum ServiceResultEnum {
    USER_NOT_FOUND("用户未找到"),
    USER_DELETED("用户已注销"),
    LOGIN_ERROR("登陆失败"),
    LOGIN_SUCCESSED_NEW_TOKEN("登陆成功，创建新的令牌"),
    LOGIN_SUCCESSED_UPDATE_TOKEN("登陆成功，更新令牌");

    private String result;
    ServiceResultEnum(String result) {
        this.result=result;
    }

    public String getResult() {
        return result;
    }
    public void setResult(String result){
        this.result=result;
    }
}

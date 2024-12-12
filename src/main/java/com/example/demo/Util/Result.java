package com.example.demo.Util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    @Schema(description = "返回数据")
    private T data;
    @Schema(description = "返回码")
    private int resultCode;
    @Schema(description = "返回信息")
    private String message;

    Result(){}
    Result(int resultCode, String message){
        this.resultCode=resultCode;
        this.message=message;
    }
}

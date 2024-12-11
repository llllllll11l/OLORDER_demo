package com.example.demo.Common;

public class CustomException extends RuntimeException{
    public CustomException(){}

    public CustomException(String message){super(message);}

    /**
     * 抛出异常
     *
     * @param message
     */
    public static void fail(String message){throw new CustomException(message);}
}

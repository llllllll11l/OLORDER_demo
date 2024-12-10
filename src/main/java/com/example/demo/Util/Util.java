package com.example.demo.Util;

import java.util.UUID;

public class Util {
    public static int genRandomNum(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

}

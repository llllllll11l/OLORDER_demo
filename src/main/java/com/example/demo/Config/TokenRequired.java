package com.example.demo.Config;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenRequired {
    /**
     * 当前用户在请求中的名字
     *
     * @return
     */
    String value() default "user";
}

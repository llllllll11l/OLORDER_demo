package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hello,world! Hello, Swagger 3!", description = "")
@RestController
public class Hello {
    @Operation
    @GetMapping("/hello")
    public String hello(){
        return "Hello, world!";
    }
}

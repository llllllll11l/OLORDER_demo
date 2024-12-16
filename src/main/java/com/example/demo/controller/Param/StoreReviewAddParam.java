package com.example.demo.controller.Param;

import com.example.demo.Enums.StoreReviewStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class StoreReviewAddParam implements Serializable {
    @NotEmpty(message = "评分不能为空")
    private int rating;
    private String comment;
}

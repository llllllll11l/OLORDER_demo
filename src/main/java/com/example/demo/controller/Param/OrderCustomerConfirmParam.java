package com.example.demo.controller.Param;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCustomerConfirmParam implements Serializable {
    @NotEmpty(message = "是否确认不能为空")
    private boolean confirm;
    private String deliveryAddress;
    private String note;
}

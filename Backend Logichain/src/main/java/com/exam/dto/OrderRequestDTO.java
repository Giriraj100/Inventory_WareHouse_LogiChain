package com.exam.dto;


import java.math.BigDecimal;

import com.exam.entities.OrderStatus;
import com.exam.entities.PaymentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {

    @NotBlank(message = "orderNumber is required")
    private String orderNumber;

    @NotNull(message = "customerId is required")
    private Long customerId;

    @NotNull(message = "orderStatus is required")
    private OrderStatus orderStatus;

    @NotNull(message = "paymentStatus is required")
    private PaymentStatus paymentStatus;

    @NotNull(message = "totalAmount is required")
    private BigDecimal totalAmount;

    @NotBlank(message = "shippingAddress is required")
    private String shippingAddress;

    @NotBlank(message = "billingAddress is required")
    private String billingAddress;
}

package com.exam.dto;

import java.math.BigDecimal;

import com.exam.entities.ReturnStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnDTO {

    private Long id;
    private Long orderId;
    private String returnNumber;
    private ReturnStatus returnStatus;
    private String reason;
    private BigDecimal refundAmount;
}

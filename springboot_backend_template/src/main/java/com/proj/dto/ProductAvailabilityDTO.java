package com.proj.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAvailabilityDTO {

    private Long productId;

    private Integer totalQuantity;
    private Integer totalReserved;
    private Integer totalAvailable;
}
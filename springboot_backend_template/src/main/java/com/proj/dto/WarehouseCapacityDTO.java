package com.proj.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseCapacityDTO {

    private Long warehouseId;

    private Integer totalCapacity;
    private Integer utilizedCapacity;
    private Integer remainingCapacity;
}
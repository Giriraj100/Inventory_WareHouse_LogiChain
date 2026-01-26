package com.proj.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {

    private Long id;

    private Long productId;
    private Long warehouseId;

    private Integer quantity;
    private Integer reservedQuantity;
    private LocalDateTime createdAt;
}
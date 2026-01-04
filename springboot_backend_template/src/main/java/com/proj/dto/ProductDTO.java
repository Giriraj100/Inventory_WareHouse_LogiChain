package com.proj.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Double weight;
    private Boolean active;
    private LocalDateTime createdAt;
}
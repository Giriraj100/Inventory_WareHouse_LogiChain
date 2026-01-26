package com.proj.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDTO {
    private Long id;
    private String code;
    private String name;
    private String location;
    private Integer capacity;
    private LocalDateTime createdAt;
}
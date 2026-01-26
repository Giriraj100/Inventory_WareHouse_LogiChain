package com.proj.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarrierDTO {
    private Long id;
    private String carrierCode;
    private String carrierName;
    private String contactEmail;
    private Boolean active;
    private LocalDateTime createdAt;
}
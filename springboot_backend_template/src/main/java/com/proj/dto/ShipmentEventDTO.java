package com.proj.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.proj.entities.ShipmentEventType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentEventDTO {

    private Long id;
    private Long shipmentId;

    private ShipmentEventType eventType;

    private String location;
    private Double latitude;
    private Double longitude;
    private String description;

    private LocalDateTime eventTimestamp;
    private LocalDateTime createdAt;
}
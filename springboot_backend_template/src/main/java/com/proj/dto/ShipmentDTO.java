package com.proj.dto;

import lombok.*;
import java.time.LocalDateTime;

import com.proj.entities.ShipmentStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentDTO {

    private Long id;
    private String trackingNumber;
    private Long orderId;
    private Long carrierId;

    private ShipmentStatus shipmentStatus;
    private String currentLocation;

    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private LocalDateTime createdAt;
}

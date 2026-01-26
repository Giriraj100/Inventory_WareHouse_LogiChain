package com.proj.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_shipment_tracking", columnNames = "tracking_number"),
           @UniqueConstraint(name = "uk_shipment_order", columnNames = "order_id")
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique tracking number
    @Column(name = "tracking_number", nullable = false, length = 100)
    private String trackingNumber;

    // External FK to Order microservice (OneToOne logically)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    // ManyToOne -> Carrier (nullable)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_id",
            foreignKey = @ForeignKey(name = "fk_shipment_carrier"))
    private Carrier carrier;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipment_status", nullable = false, length = 50)
    private ShipmentStatus shipmentStatus;

    @Column(name = "current_location", length = 255)
    private String currentLocation;

    @Column(name = "estimated_delivery_date")
    private LocalDateTime estimatedDeliveryDate;

    @Column(name = "actual_delivery_date")
    private LocalDateTime actualDeliveryDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // optional proof, cancel reason, failure reason can go in DTO/service later if needed,
    // but core spec doesn't force it here.

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (shipmentStatus == null) {
            shipmentStatus = ShipmentStatus.CREATED;
        }
    }
}
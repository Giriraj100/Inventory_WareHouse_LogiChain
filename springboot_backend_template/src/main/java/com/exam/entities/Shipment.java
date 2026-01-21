package com.exam.entities;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipments", uniqueConstraints = {
		@UniqueConstraint(columnNames = "tracking_number"),
		@UniqueConstraint(columnNames = "order_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tracking_number", nullable = false, unique = true)
	private String trackingNumber;

	@Column(name = "order_id", nullable = false, unique = true)
	private Long orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carrier_id")
	private Carrier carrier; // nullable

	@Enumerated(EnumType.STRING)
	@Column(name = "shipment_status", nullable = false)
	private ShipmentStatus shipmentStatus;

	@Column(name = "current_location")
	private String currentLocation;

	@Column(name = "estimated_delivery_date")
	private Timestamp estimatedDeliveryDate;

	@Column(name = "actual_delivery_date")
	private Timestamp actualDeliveryDate;

	@Column(name = "created_at")
	private Timestamp createdAt;
}

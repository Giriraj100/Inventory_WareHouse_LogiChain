package com.exam.entities;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipment_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shipment_id", nullable = false)
	private Shipment shipment;

	@Enumerated(EnumType.STRING)
	@Column(name = "event_type", nullable = false)
	private ShipmentEventType eventType;

	private String location;

	private Double latitude;

	private Double longitude;

	private String description;

	@Column(name = "event_timestamp")
	private Timestamp eventTimestamp;

	@Column(name = "created_at")
	private Timestamp createdAt;
}

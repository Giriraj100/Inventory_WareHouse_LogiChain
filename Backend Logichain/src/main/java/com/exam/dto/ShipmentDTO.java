package com.exam.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentDTO {
	private Long id;
	private String trackingNumber;
	private Long orderId;
	private Long carrierId;
	private String shipmentStatus;
	private String currentLocation;
	private Timestamp estimatedDeliveryDate;
	private Timestamp actualDeliveryDate;
	private Timestamp createdAt;
}

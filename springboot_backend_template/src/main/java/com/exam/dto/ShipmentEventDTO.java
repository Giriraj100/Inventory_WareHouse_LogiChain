package com.exam.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentEventDTO {
	private Long id;
	private Long shipmentId;
	private String eventType;
	private String location;
	private Double latitude;
	private Double longitude;
	private String description;
	private Timestamp eventTimestamp;
	private Timestamp createdAt;
}

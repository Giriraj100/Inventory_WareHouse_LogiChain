package com.exam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentStatusDTO {
	private String status;     // CREATED, IN_TRANSIT, DELIVERED, FAILED
	private String location;
}

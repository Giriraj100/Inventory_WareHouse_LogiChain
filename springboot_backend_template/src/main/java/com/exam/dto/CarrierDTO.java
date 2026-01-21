package com.exam.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarrierDTO {
	private Long id;
	private String carrierCode;
	private String carrierName;
	private String contactEmail;
	private Timestamp createdAt;
	private Boolean isActive;
}

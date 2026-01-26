package com.exam.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseDTO {
	private Long id;
	private String code;
	private String name;
	private String location;
	private Integer capacity;
	private Timestamp createdAt;
}

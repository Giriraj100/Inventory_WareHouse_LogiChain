package com.exam.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
	private Long id;
	private String sku;
	private String name;
	private BigDecimal price;
	private Double weight;
	private Timestamp createdAt;
	private Boolean isActive;
}

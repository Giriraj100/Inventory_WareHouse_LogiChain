package com.exam.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDTO {
	private Long id;
	private Long productId;
	private Long warehouseId;
	private Integer quantity;
	private Integer reservedQuantity;
	private Timestamp createdAt;
}

package com.exam.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseCapacityDTO {
	private Long warehouseId;
	private Integer capacity;
	private Integer totalStock;
	private Integer remainingCapacity;
}

package com.exam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveStockDTO {
	private Integer quantity;
	private Long orderId;
}

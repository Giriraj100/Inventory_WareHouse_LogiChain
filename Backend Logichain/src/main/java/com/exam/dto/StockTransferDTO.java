package com.exam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockTransferDTO {
	private Long productId;
	private Long fromWarehouseId;
	private Long toWarehouseId;
	private Integer quantity;
}

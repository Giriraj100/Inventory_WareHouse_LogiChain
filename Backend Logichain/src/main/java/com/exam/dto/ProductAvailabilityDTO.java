package com.exam.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAvailabilityDTO {
	private Long productId;
	private Integer totalStock;
	private Integer totalReserved;
	private Integer availableStock;
}

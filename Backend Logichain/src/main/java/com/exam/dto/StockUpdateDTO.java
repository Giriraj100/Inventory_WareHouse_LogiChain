package com.exam.dto;

import com.exam.entities.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateDTO {
	private Integer quantityChange;
	private TransactionType type;
}

package com.exam.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryTransactionDTO {
	private Long id;
	private Long inventoryId;
	private String transactionType;
	private Integer quantityChanged;
	private Long referenceId;
	private Timestamp timestamp;
}

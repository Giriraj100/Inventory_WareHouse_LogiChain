package com.exam.service;

import java.util.List;

import org.springframework.data.domain.*;

import com.exam.dto.InventoryTransactionDTO;

public interface InventoryTransactionService {

	InventoryTransactionDTO createTransaction(InventoryTransactionDTO dto);

	Page<InventoryTransactionDTO> getInventoryTransactions(Long inventoryId, Pageable pageable);

	InventoryTransactionDTO getTransactionById(Long id);

	List<InventoryTransactionDTO> getTransactionsByReferenceId(Long referenceId, String referenceType);

	String deleteTransaction(Long id);
}

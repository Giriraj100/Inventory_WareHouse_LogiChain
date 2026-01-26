package com.exam.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.custom_exceptions.ResourceNotFoundException;
import com.exam.dto.InventoryTransactionDTO;
import com.exam.entities.*;
import com.exam.repository.InventoryRepository;
import com.exam.repository.InventoryTransactionRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

	private final InventoryTransactionRepository inventoryTransactionRepository;
	private final InventoryRepository inventoryRepository;

	@Override
	public InventoryTransactionDTO createTransaction(InventoryTransactionDTO dto) {

		Inventory inv = inventoryRepository.findById(dto.getInventoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found with ID: " + dto.getInventoryId()));

		InventoryTransaction tx = new InventoryTransaction();
		tx.setInventory(inv);
		tx.setTransactionType(TransactionType.valueOf(dto.getTransactionType().toUpperCase()));
		tx.setQuantityChanged(dto.getQuantityChanged());
		tx.setReferenceId(dto.getReferenceId());
		tx.setTimestamp(new Timestamp(System.currentTimeMillis()));

		return toDTO(inventoryTransactionRepository.save(tx));
	}

	@Override
	public Page<InventoryTransactionDTO> getInventoryTransactions(Long inventoryId, Pageable pageable) {
		return inventoryTransactionRepository.findByInventoryId(inventoryId, pageable)
				.map(this::toDTO);
	}

	@Override
	public InventoryTransactionDTO getTransactionById(Long id) {
		InventoryTransaction tx = inventoryTransactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));
		return toDTO(tx);
	}

	@Override
	public List<InventoryTransactionDTO> getTransactionsByReferenceId(Long referenceId, String referenceType) {
		return inventoryTransactionRepository.findByReferenceId(referenceId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public String deleteTransaction(Long id) {
		InventoryTransaction tx = inventoryTransactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));
		inventoryTransactionRepository.delete(tx);
		return "Transaction deleted successfully";
	}

	private InventoryTransactionDTO toDTO(InventoryTransaction tx) {
		InventoryTransactionDTO dto = new InventoryTransactionDTO();
		dto.setId(tx.getId());
		dto.setInventoryId(tx.getInventory().getId());
		dto.setTransactionType(tx.getTransactionType().name());
		dto.setQuantityChanged(tx.getQuantityChanged());
		dto.setReferenceId(tx.getReferenceId());
		dto.setTimestamp(tx.getTimestamp());
		return dto;
	}
}

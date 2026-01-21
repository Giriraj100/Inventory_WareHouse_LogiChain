package com.exam.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.ApiResponse;
import com.exam.dto.InventoryTransactionDTO;
import com.exam.service.InventoryTransactionService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/inventory-transactions")
@AllArgsConstructor
public class InventoryTransactionController {

	private final InventoryTransactionService inventoryTransactionService;

	/*
	 * Desc - Create transaction
	 * URL - /api/v1/inventory-transactions
	 * Method - POST
	 */
	@PostMapping
	public ResponseEntity<?> createTransaction(@RequestBody @Valid InventoryTransactionDTO dto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(inventoryTransactionService.createTransaction(dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get transactions by inventoryId
	 * URL - /api/v1/inventory-transactions?inventoryId=
	 * Method - GET
	 */
	@GetMapping
	public ResponseEntity<?> getInventoryTransactions(@RequestParam Long inventoryId, Pageable pageable) {
		try {
			return ResponseEntity.ok(inventoryTransactionService.getInventoryTransactions(inventoryId, pageable));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get transaction by ID
	 * URL - /api/v1/inventory-transactions/{id}
	 * Method - GET
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(inventoryTransactionService.getTransactionById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get transactions by referenceId
	 * URL - /api/v1/inventory-transactions/reference/{referenceId}?referenceType=
	 * Method - GET
	 */
	@GetMapping("/reference/{referenceId}")
	public ResponseEntity<?> getTransactionsByReferenceId(@PathVariable Long referenceId,
														 @RequestParam(required = false) String referenceType) {
		try {
			return ResponseEntity.ok(inventoryTransactionService.getTransactionsByReferenceId(referenceId, referenceType));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Delete transaction
	 * URL - /api/v1/inventory-transactions/{id}
	 * Method - DELETE
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(new ApiResponse(inventoryTransactionService.deleteTransaction(id), "success"));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}
}


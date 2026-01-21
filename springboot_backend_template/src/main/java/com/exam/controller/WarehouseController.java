package com.exam.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.ApiResponse;
import com.exam.dto.WarehouseDTO;
import com.exam.service.WarehouseService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/warehouses")
@AllArgsConstructor
public class WarehouseController {

	private final WarehouseService warehouseService;

	/*
	 * Desc - Create warehouse
	 * URL - /api/v1/warehouses
	 * Method - POST
	 */
	@PostMapping
	public ResponseEntity<?> createWarehouse(@RequestBody @Valid WarehouseDTO dto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(warehouseService.createWarehouse(dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get all warehouses
	 * URL - /api/v1/warehouses
	 * Method - GET
	 */
	@GetMapping
	public ResponseEntity<?> getAllWarehouses(Pageable pageable) {
		try {
			return ResponseEntity.ok(warehouseService.getAllWarehouses(pageable));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get warehouse by ID
	 * URL - /api/v1/warehouses/{id}
	 * Method - GET
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getWarehouseById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(warehouseService.getWarehouseById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get warehouse by code
	 * URL - /api/v1/warehouses/code/{code}
	 * Method - GET
	 */
	@GetMapping("/code/{code}")
	public ResponseEntity<?> getWarehouseByCode(@PathVariable String code) {
		try {
			return ResponseEntity.ok(warehouseService.getWarehouseByCode(code));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Update warehouse
	 * URL - /api/v1/warehouses/{id}
	 * Method - PUT
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateWarehouse(@PathVariable Long id, @RequestBody WarehouseDTO dto) {
		try {
			return ResponseEntity.ok(warehouseService.updateWarehouse(id, dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Delete warehouse
	 * URL - /api/v1/warehouses/{id}
	 * Method - DELETE
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteWarehouse(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(new ApiResponse(warehouseService.deleteWarehouse(id), "success"));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get warehouse capacity
	 * URL - /api/v1/warehouses/{id}/capacity
	 * Method - GET
	 */
	@GetMapping("/{id}/capacity")
	public ResponseEntity<?> getWarehouseCapacity(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(warehouseService.getWarehouseCapacity(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}
}

//package com.exam.controller;
//
//
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//import com.exam.dto.*;
//import com.exam.service.InventoryService;
//
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//
//@RestController
//@RequestMapping("/api/v1/inventory")
//@AllArgsConstructor
//public class InventoryController {
//
//	private final InventoryService inventoryService;
//
//	/*
//	 * Desc - Add inventory
//	 * URL - /api/v1/inventory
//	 * Method - POST
//	 */
//	@PostMapping
//	public ResponseEntity<?> addInventory(@RequestBody @Valid InventoryDTO dto) {
//		try {
//			return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addInventory(dto));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get all inventory (filters optional)
//	 * URL - /api/v1/inventory?warehouseId=&productId=
//	 * Method - GET
//	 */
//	@GetMapping
//	public ResponseEntity<?> getAllInventory(Pageable pageable,
//											@RequestParam(required = false) Long warehouseId,
//											@RequestParam(required = false) Long productId) {
//		try {
//			return ResponseEntity.ok(inventoryService.getAllInventory(pageable, warehouseId, productId));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get inventory by ID
//	 * URL - /api/v1/inventory/{id}
//	 * Method - GET
//	 */
//	@GetMapping("/{id}")
//	public ResponseEntity<?> getInventoryById(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(inventoryService.getInventoryById(id));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Update stock
//	 * URL - /api/v1/inventory/{id}/stock
//	 * Method - PUT
//	 */
//	@PutMapping("/{id}/stock")
//	public ResponseEntity<?> updateStockQuantity(@PathVariable Long id, @RequestBody StockUpdateDTO dto) {
//		try {
//			return ResponseEntity.ok(inventoryService.updateStockQuantity(id, dto.getQuantityChange(), dto.getType()));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Reserve stock
//	 * URL - /api/v1/inventory/{id}/reserve
//	 * Method - POST
//	 */
//	@PostMapping("/{id}/reserve")
//	public ResponseEntity<?> reserveStock(@PathVariable Long id, @RequestBody ReserveStockDTO dto) {
//		try {
//			return ResponseEntity.ok(inventoryService.reserveStock(id, dto.getQuantity(), dto.getOrderId()));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Release stock
//	 * URL - /api/v1/inventory/{id}/release
//	 * Method - POST
//	 */
//	@PostMapping("/{id}/release")
//	public ResponseEntity<?> releaseStock(@PathVariable Long id, @RequestBody ReleaseStockDTO dto) {
//		try {
//			return ResponseEntity.ok(inventoryService.releaseStock(id, dto.getQuantity(), dto.getOrderId()));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Low stock items
//	 * URL - /api/v1/inventory/low-stock?warehouseId=
//	 * Method - GET
//	 */
//	@GetMapping("/low-stock")
//	public ResponseEntity<?> getLowStockItems(@RequestParam Long warehouseId) {
//		try {
//			return ResponseEntity.ok(inventoryService.getLowStockItems(warehouseId));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Transfer stock between warehouses
//	 * URL - /api/v1/inventory/transfer
//	 * Method - POST
//	 */
//	@PostMapping("/transfer")
//	public ResponseEntity<?> transferStock(@RequestBody StockTransferDTO dto) {
//		try {
//			String msg = inventoryService.transferStock(dto.getProductId(), dto.getFromWarehouseId(),
//					dto.getToWarehouseId(), dto.getQuantity());
//			return ResponseEntity.ok(new ApiResponse(msg, "success"));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Check availability
//	 * URL - /api/v1/inventory/check-availability?productId=&warehouseId=&quantity=
//	 * Method - GET
//	 */
//	@GetMapping("/check-availability")
//	public ResponseEntity<?> checkStockAvailability(@RequestParam Long productId,
//												   @RequestParam Long warehouseId,
//												   @RequestParam Integer quantity) {
//		try {
//			return ResponseEntity.ok(inventoryService.checkStockAvailability(productId, warehouseId, quantity));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//}
package com.exam.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.*;
import com.exam.service.InventoryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/inventory")
@AllArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	/*
	 * Desc - Add inventory
	 * URL - /api/v1/inventory
	 * Method - POST
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PostMapping
	public ResponseEntity<?> addInventory(@RequestBody @Valid InventoryDTO dto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addInventory(dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get all inventory (filters optional)
	 * URL - /api/v1/inventory?warehouseId=&productId=
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<?> getAllInventory(Pageable pageable,
											@RequestParam(required = false) Long warehouseId,
											@RequestParam(required = false) Long productId) {
		try {
			return ResponseEntity.ok(inventoryService.getAllInventory(pageable, warehouseId, productId));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get inventory by ID
	 * URL - /api/v1/inventory/{id}
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}")
	public ResponseEntity<?> getInventoryById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(inventoryService.getInventoryById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Update stock
	 * URL - /api/v1/inventory/{id}/stock
	 * Method - PUT
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PutMapping("/{id}/stock")
	public ResponseEntity<?> updateStockQuantity(@PathVariable Long id, @RequestBody StockUpdateDTO dto) {
		try {
			return ResponseEntity.ok(inventoryService.updateStockQuantity(id, dto.getQuantityChange(), dto.getType()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Reserve stock
	 * URL - /api/v1/inventory/{id}/reserve
	 * Method - POST
	 * Authorization: ADMIN (internal service calls OR ADMIN)
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{id}/reserve")
	public ResponseEntity<?> reserveStock(@PathVariable Long id, @RequestBody ReserveStockDTO dto) {
		try {
			return ResponseEntity.ok(inventoryService.reserveStock(id, dto.getQuantity(), dto.getOrderId()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Release stock
	 * URL - /api/v1/inventory/{id}/release
	 * Method - POST
	 * Authorization: ADMIN (internal service calls OR ADMIN)
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{id}/release")
	public ResponseEntity<?> releaseStock(@PathVariable Long id, @RequestBody ReleaseStockDTO dto) {
		try {
			return ResponseEntity.ok(inventoryService.releaseStock(id, dto.getQuantity(), dto.getOrderId()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Low stock items
	 * URL - /api/v1/inventory/low-stock?warehouseId=
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/low-stock")
	public ResponseEntity<?> getLowStockItems(@RequestParam Long warehouseId) {
		try {
			return ResponseEntity.ok(inventoryService.getLowStockItems(warehouseId));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Transfer stock between warehouses
	 * URL - /api/v1/inventory/transfer
	 * Method - POST
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PostMapping("/transfer")
	public ResponseEntity<?> transferStock(@RequestBody StockTransferDTO dto) {
		try {
			String msg = inventoryService.transferStock(dto.getProductId(), dto.getFromWarehouseId(),
					dto.getToWarehouseId(), dto.getQuantity());
			return ResponseEntity.ok(new ApiResponse(msg, "success"));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Check availability
	 * URL - /api/v1/inventory/check-availability?productId=&warehouseId=&quantity=
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/check-availability")
	public ResponseEntity<?> checkStockAvailability(@RequestParam Long productId,
												   @RequestParam Long warehouseId,
												   @RequestParam Integer quantity) {
		try {
			return ResponseEntity.ok(inventoryService.checkStockAvailability(productId, warehouseId, quantity));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}
}

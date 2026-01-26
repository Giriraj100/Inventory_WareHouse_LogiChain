//package com.exam.controller;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//import com.exam.dto.*;
//import com.exam.service.ProductService;
//
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//
//@RestController
//@RequestMapping("/api/v1/products")
//@AllArgsConstructor
//public class ProductController {
//
//	private final ProductService productService;
//
//	/*
//	 * Desc - Create product
//	 * URL - /api/v1/products
//	 * Method - POST
//	 */
//	@PostMapping
//	public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO dto) {
//		try {
//			return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(dto));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get all products
//	 * URL - /api/v1/products
//	 * Method - GET
//	 */
//	@GetMapping
//	public ResponseEntity<?> getAllProducts(Pageable pageable, @RequestParam(required = false) String search) {
//		try {
//			return ResponseEntity.ok(productService.getAllProducts(pageable, search));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get product by ID
//	 * URL - /api/v1/products/{id}
//	 * Method - GET
//	 */
//	@GetMapping("/{id}")
//	public ResponseEntity<?> getProductById(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(productService.getProductById(id));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get product by SKU
//	 * URL - /api/v1/products/sku/{sku}
//	 * Method - GET
//	 */
//	@GetMapping("/sku/{sku}")
//	public ResponseEntity<?> getProductBySku(@PathVariable String sku) {
//		try {
//			return ResponseEntity.ok(productService.getProductBySku(sku));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Update product
//	 * URL - /api/v1/products/{id}
//	 * Method - PUT
//	 */
//	@PutMapping("/{id}")
//	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
//		try {
//			return ResponseEntity.ok(productService.updateProduct(id, dto));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Delete product
//	 * URL - /api/v1/products/{id}
//	 * Method - DELETE
//	 */
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(new ApiResponse(productService.deleteProduct(id), "success"));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Product availability
//	 * URL - /api/v1/products/{id}/availability
//	 * Method - GET
//	 */
//	@GetMapping("/{id}/availability")
//	public ResponseEntity<?> getProductAvailability(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(productService.getProductAvailability(id));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Activate/Deactivate product
//	 * URL - /api/v1/products/{id}/status?isActive=true/false
//	 * Method - PATCH
//	 */
//	@PatchMapping("/{id}/status")
//	public ResponseEntity<?> activateDeactivateProduct(@PathVariable Long id, @RequestParam boolean isActive) {
//		try {
//			return ResponseEntity.ok(productService.activateDeactivateProduct(id, isActive));
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
import com.exam.service.ProductService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;

	/*
	 * Desc - Create product
	 * URL - /api/v1/products
	 * Method - POST
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO dto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get all products
	 * URL - /api/v1/products
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<?> getAllProducts(Pageable pageable, @RequestParam(required = false) String search) {
		try {
			return ResponseEntity.ok(productService.getAllProducts(pageable, search));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get product by ID
	 * URL - /api/v1/products/{id}
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(productService.getProductById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get product by SKU
	 * URL - /api/v1/products/sku/{sku}
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/sku/{sku}")
	public ResponseEntity<?> getProductBySku(@PathVariable String sku) {
		try {
			return ResponseEntity.ok(productService.getProductBySku(sku));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Update product
	 * URL - /api/v1/products/{id}
	 * Method - PUT
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
		try {
			return ResponseEntity.ok(productService.updateProduct(id, dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Delete product
	 * URL - /api/v1/products/{id}
	 * Method - DELETE
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(new ApiResponse(productService.deleteProduct(id), "success"));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Product availability
	 * URL - /api/v1/products/{id}/availability
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}/availability")
	public ResponseEntity<?> getProductAvailability(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(productService.getProductAvailability(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Activate/Deactivate product
	 * URL - /api/v1/products/{id}/status?isActive=true/false
	 * Method - PATCH
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PatchMapping("/{id}/status")
	public ResponseEntity<?> activateDeactivateProduct(@PathVariable Long id, @RequestParam boolean isActive) {
		try {
			return ResponseEntity.ok(productService.activateDeactivateProduct(id, isActive));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}
}

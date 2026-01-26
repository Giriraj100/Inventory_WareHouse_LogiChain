//package com.exam.controller;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//import com.exam.dto.ApiResponse;
//import com.exam.dto.CarrierDTO;
//import com.exam.service.CarrierService;
//
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//
//@RestController
//@RequestMapping("/api/v1/carriers")
//@AllArgsConstructor
//public class CarrierController {
//
//	private final CarrierService carrierService;
//
//	/*
//	 * Desc - Create carrier
//	 * URL - /api/v1/carriers
//	 * Method - POST
//	 */
//	@PostMapping
//	public ResponseEntity<?> createCarrier(@RequestBody @Valid CarrierDTO dto) {
//		try {
//			return ResponseEntity.status(HttpStatus.CREATED).body(carrierService.createCarrier(dto));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get all carriers
//	 * URL - /api/v1/carriers
//	 * Method - GET
//	 */
//	@GetMapping
//	public ResponseEntity<?> getAllCarriers(Pageable pageable) {
//		try {
//			return ResponseEntity.ok(carrierService.getAllCarriers(pageable));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get carrier by ID
//	 * URL - /api/v1/carriers/{id}
//	 * Method - GET
//	 */
//	@GetMapping("/{id}")
//	public ResponseEntity<?> getCarrierById(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(carrierService.getCarrierById(id));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get carrier by code
//	 * URL - /api/v1/carriers/code/{carrierCode}
//	 * Method - GET
//	 */
//	@GetMapping("/code/{carrierCode}")
//	public ResponseEntity<?> getCarrierByCode(@PathVariable String carrierCode) {
//		try {
//			return ResponseEntity.ok(carrierService.getCarrierByCode(carrierCode));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Update carrier
//	 * URL - /api/v1/carriers/{id}
//	 * Method - PUT
//	 */
//	@PutMapping("/{id}")
//	public ResponseEntity<?> updateCarrier(@PathVariable Long id, @RequestBody CarrierDTO dto) {
//		try {
//			return ResponseEntity.ok(carrierService.updateCarrier(id, dto));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Delete carrier
//	 * URL - /api/v1/carriers/{id}
//	 * Method - DELETE
//	 */
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteCarrier(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(new ApiResponse(carrierService.deleteCarrier(id), "success"));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Activate/Deactivate carrier
//	 * URL - /api/v1/carriers/{id}/status?isActive=true/false
//	 * Method - PATCH
//	 */
//	@PatchMapping("/{id}/status")
//	public ResponseEntity<?> activateDeactivateCarrier(@PathVariable Long id, @RequestParam boolean isActive) {
//		try {
//			return ResponseEntity.ok(carrierService.activateDeactivateCarrier(id, isActive));
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

import com.exam.dto.ApiResponse;
import com.exam.dto.CarrierDTO;
import com.exam.service.CarrierService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/carriers")
@AllArgsConstructor
public class CarrierController {

	private final CarrierService carrierService;

	/*
	 * Desc - Create carrier
	 * URL - /api/v1/carriers
	 * Method - POST
	 * Authorization: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> createCarrier(@RequestBody @Valid CarrierDTO dto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(carrierService.createCarrier(dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get all carriers
	 * URL - /api/v1/carriers
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<?> getAllCarriers(Pageable pageable) {
		try {
			return ResponseEntity.ok(carrierService.getAllCarriers(pageable));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get carrier by ID
	 * URL - /api/v1/carriers/{id}
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}")
	public ResponseEntity<?> getCarrierById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(carrierService.getCarrierById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get carrier by code
	 * URL - /api/v1/carriers/code/{carrierCode}
	 * Method - GET
	 * Authorization: Any authenticated user
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/code/{carrierCode}")
	public ResponseEntity<?> getCarrierByCode(@PathVariable String carrierCode) {
		try {
			return ResponseEntity.ok(carrierService.getCarrierByCode(carrierCode));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Update carrier
	 * URL - /api/v1/carriers/{id}
	 * Method - PUT
	 * Authorization: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCarrier(@PathVariable Long id, @RequestBody CarrierDTO dto) {
		try {
			return ResponseEntity.ok(carrierService.updateCarrier(id, dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Delete carrier
	 * URL - /api/v1/carriers/{id}
	 * Method - DELETE
	 * Authorization: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCarrier(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(new ApiResponse(carrierService.deleteCarrier(id), "success"));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Activate/Deactivate carrier
	 * URL - /api/v1/carriers/{id}/status?isActive=true/false
	 * Method - PATCH
	 * Authorization: ADMIN
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}/status")
	public ResponseEntity<?> activateDeactivateCarrier(@PathVariable Long id, @RequestParam boolean isActive) {
		try {
			return ResponseEntity.ok(carrierService.activateDeactivateCarrier(id, isActive));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}
}

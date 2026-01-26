//package com.exam.controller;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//
//import com.exam.dto.*;
//import com.exam.entities.ShipmentStatus;
//import com.exam.service.ShipmentService;
//
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//
//@RestController
//@RequestMapping("/api/v1/shipments")
//@AllArgsConstructor
//public class ShipmentController {
//
//	private final ShipmentService shipmentService;
//
//	/*
//	 * Desc - Create shipment
//	 * URL - /api/v1/shipments
//	 * Method - POST
//	 */
//	@PostMapping
//	public ResponseEntity<?> createShipment(@RequestBody @Valid ShipmentDTO dto) {
//		try {
//			return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.createShipment(dto));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get all shipments
//	 * URL - /api/v1/shipments?status=&orderId=
//	 * Method - GET
//	 */
//	@GetMapping
//	public ResponseEntity<?> getAllShipments(Pageable pageable,
//											@RequestParam(required = false) ShipmentStatus status,
//											@RequestParam(required = false) Long orderId) {
//		try {
//			return ResponseEntity.ok(shipmentService.getAllShipments(pageable, status, orderId));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get shipment by ID
//	 * URL - /api/v1/shipments/{id}
//	 * Method - GET
//	 */
//	@GetMapping("/{id}")
//	public ResponseEntity<?> getShipmentById(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(shipmentService.getShipmentById(id));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get shipment by tracking number
//	 * URL - /api/v1/shipments/track/{trackingNumber}
//	 * Method - GET
//	 */
//	@GetMapping("/track/{trackingNumber}")
//	public ResponseEntity<?> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
//		try {
//			return ResponseEntity.ok(shipmentService.getShipmentByTrackingNumber(trackingNumber));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Get shipment by orderId
//	 * URL - /api/v1/shipments/order/{orderId}
//	 * Method - GET
//	 */
//	@GetMapping("/order/{orderId}")
//	public ResponseEntity<?> getShipmentByOrderId(@PathVariable Long orderId) {
//		try {
//			return ResponseEntity.ok(shipmentService.getShipmentByOrderId(orderId));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Update shipment status
//	 * URL - /api/v1/shipments/{id}/status
//	 * Method - PUT
//	 */
//	@PutMapping("/{id}/status")
//	public ResponseEntity<?> updateShipmentStatus(@PathVariable Long id, @RequestBody ShipmentStatusDTO dto) {
//		try {
//			ShipmentStatus status = ShipmentStatus.valueOf(dto.getStatus().toUpperCase());
//			return ResponseEntity.ok(shipmentService.updateShipmentStatus(id, status, dto.getLocation()));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Update shipment location
//	 * URL - /api/v1/shipments/{id}/location
//	 * Method - PUT
//	 */
//	@PutMapping("/{id}/location")
//	public ResponseEntity<?> updateShipmentLocation(@PathVariable Long id, @RequestBody LocationDTO dto) {
//		try {
//			return ResponseEntity.ok(shipmentService.updateShipmentLocation(id, dto.getLocation(), dto.getLatitude(), dto.getLongitude()));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Mark delivered
//	 * URL - /api/v1/shipments/{id}/deliver
//	 * Method - POST
//	 */
//	@PostMapping("/{id}/deliver")
//	public ResponseEntity<?> markAsDelivered(@PathVariable Long id, @RequestBody DeliveryProofDTO dto) {
//		try {
//			return ResponseEntity.ok(shipmentService.markAsDelivered(id, dto.getProofOfDeliveryUrl()));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Mark failed
//	 * URL - /api/v1/shipments/{id}/fail
//	 * Method - POST
//	 */
//	@PostMapping("/{id}/fail")
//	public ResponseEntity<?> markAsFailed(@PathVariable Long id, @RequestBody FailureReasonDTO dto) {
//		try {
//			return ResponseEntity.ok(shipmentService.markAsFailed(id, dto.getReason()));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Cancel shipment
//	 * URL - /api/v1/shipments/{id}/cancel
//	 * Method - POST
//	 */
//	@PostMapping("/{id}/cancel")
//	public ResponseEntity<?> cancelShipment(@PathVariable Long id, @RequestBody CancelReasonDTO dto) {
//		try {
//			return ResponseEntity.ok(shipmentService.cancelShipment(id, dto.getReason()));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body(new ApiResponse(e.getMessage(), "failed"));
//		}
//	}
//
//	/*
//	 * Desc - Delete shipment
//	 * URL - /api/v1/shipments/{id}
//	 * Method - DELETE
//	 */
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteShipment(@PathVariable Long id) {
//		try {
//			return ResponseEntity.ok(new ApiResponse(shipmentService.deleteShipment(id), "success"));
//		} catch (RuntimeException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
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
import com.exam.entities.ShipmentStatus;
import com.exam.service.ShipmentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/shipments")
@AllArgsConstructor
public class ShipmentController {

	private final ShipmentService shipmentService;

	/*
	 * Desc - Create shipment
	 * URL - /api/v1/shipments
	 * Method - POST
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PostMapping
	public ResponseEntity<?> createShipment(@RequestBody @Valid ShipmentDTO dto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(shipmentService.createShipment(dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get all shipments
	 * URL - /api/v1/shipments?status=&orderId=
	 * Method - GET
	 * Authorization: ADMIN, WAREHOUSE_MANAGER, CUSTOMER_SUPPORT
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER','CUSTOMER_SUPPORT')")
	@GetMapping
	public ResponseEntity<?> getAllShipments(Pageable pageable,
											@RequestParam(required = false) ShipmentStatus status,
											@RequestParam(required = false) Long orderId) {
		try {
			return ResponseEntity.ok(shipmentService.getAllShipments(pageable, status, orderId));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get shipment by ID
	 * URL - /api/v1/shipments/{id}
	 * Method - GET
	 * Authorization: ADMIN, WAREHOUSE_MANAGER, CUSTOMER_SUPPORT
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER','CUSTOMER_SUPPORT')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getShipmentById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(shipmentService.getShipmentById(id));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get shipment by tracking number
	 * URL - /api/v1/shipments/track/{trackingNumber}
	 * Method - GET
	 * Authorization: Public OR CUSTOMER_SUPPORT OR CUSTOMER
	 *
	 * NOTE: You said Public OR CUSTOMER (own shipments).
	 * Here we allow Public for now.
	 */
	@PreAuthorize("permitAll()")
	@GetMapping("/track/{trackingNumber}")
	public ResponseEntity<?> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
		try {
			return ResponseEntity.ok(shipmentService.getShipmentByTrackingNumber(trackingNumber));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get shipment by orderId
	 * URL - /api/v1/shipments/order/{orderId}
	 * Method - GET
	 * Authorization: ADMIN, WAREHOUSE_MANAGER, CUSTOMER_SUPPORT
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER','CUSTOMER_SUPPORT')")
	@GetMapping("/order/{orderId}")
	public ResponseEntity<?> getShipmentByOrderId(@PathVariable Long orderId) {
		try {
			return ResponseEntity.ok(shipmentService.getShipmentByOrderId(orderId));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Update shipment status
	 * URL - /api/v1/shipments/{id}/status
	 * Method - PUT
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PutMapping("/{id}/status")
	public ResponseEntity<?> updateShipmentStatus(@PathVariable Long id, @RequestBody ShipmentStatusDTO dto) {
		try {
			ShipmentStatus status = ShipmentStatus.valueOf(dto.getStatus().toUpperCase());
			return ResponseEntity.ok(shipmentService.updateShipmentStatus(id, status, dto.getLocation()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Update shipment location
	 * URL - /api/v1/shipments/{id}/location
	 * Method - PUT
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PutMapping("/{id}/location")
	public ResponseEntity<?> updateShipmentLocation(@PathVariable Long id, @RequestBody LocationDTO dto) {
		try {
			return ResponseEntity.ok(
					shipmentService.updateShipmentLocation(id, dto.getLocation(), dto.getLatitude(), dto.getLongitude())
			);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Mark delivered
	 * URL - /api/v1/shipments/{id}/deliver
	 * Method - POST
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PostMapping("/{id}/deliver")
	public ResponseEntity<?> markAsDelivered(@PathVariable Long id, @RequestBody DeliveryProofDTO dto) {
		try {
			return ResponseEntity.ok(shipmentService.markAsDelivered(id, dto.getProofOfDeliveryUrl()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Mark failed
	 * URL - /api/v1/shipments/{id}/fail
	 * Method - POST
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PostMapping("/{id}/fail")
	public ResponseEntity<?> markAsFailed(@PathVariable Long id, @RequestBody FailureReasonDTO dto) {
		try {
			return ResponseEntity.ok(shipmentService.markAsFailed(id, dto.getReason()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Cancel shipment
	 * URL - /api/v1/shipments/{id}/cancel
	 * Method - POST
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@PostMapping("/{id}/cancel")
	public ResponseEntity<?> cancelShipment(@PathVariable Long id, @RequestBody CancelReasonDTO dto) {
		try {
			return ResponseEntity.ok(shipmentService.cancelShipment(id, dto.getReason()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Delete shipment
	 * URL - /api/v1/shipments/{id}
	 * Method - DELETE
	 * Authorization: ADMIN, WAREHOUSE_MANAGER
	 */
	@PreAuthorize("hasAnyRole('ADMIN','WAREHOUSE_MANAGER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteShipment(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(new ApiResponse(shipmentService.deleteShipment(id), "success"));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}
}

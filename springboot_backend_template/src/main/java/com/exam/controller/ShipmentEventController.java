package com.exam.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.ApiResponse;
import com.exam.dto.ShipmentEventDTO;
import com.exam.service.ShipmentEventService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/shipments/{shipmentId}/events")
@AllArgsConstructor
public class ShipmentEventController {

	private final ShipmentEventService shipmentEventService;

	/*
	 * Desc - Create shipment event (manual)
	 * URL - /api/v1/shipments/{shipmentId}/events
	 * Method - POST
	 */
	@PostMapping
	public ResponseEntity<?> createEvent(@PathVariable Long shipmentId,
										@RequestBody @Valid ShipmentEventDTO dto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(shipmentEventService.createEvent(shipmentId, dto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get shipment events by shipmentId
	 * URL - /api/v1/shipments/{shipmentId}/events
	 * Method - GET
	 */
	@GetMapping
	public ResponseEntity<?> getShipmentEvents(@PathVariable Long shipmentId, Pageable pageable) {
		try {
			return ResponseEntity.ok(shipmentEventService.getShipmentEvents(shipmentId, pageable));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get event by eventId
	 * URL - /api/v1/shipments/{shipmentId}/events/{eventId}
	 * Method - GET
	 */
	@GetMapping("/{eventId}")
	public ResponseEntity<?> getEventById(@PathVariable Long shipmentId,
										 @PathVariable Long eventId) {
		try {
			return ResponseEntity.ok(shipmentEventService.getEventById(eventId));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Get latest event for shipmentId
	 * URL - /api/v1/shipments/{shipmentId}/events/latest
	 * Method - GET
	 */
	@GetMapping("/latest")
	public ResponseEntity<?> getLatestEvent(@PathVariable Long shipmentId) {
		try {
			return ResponseEntity.ok(shipmentEventService.getLatestEvent(shipmentId));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}

	/*
	 * Desc - Delete shipment event by ID
	 * URL - /api/v1/shipments/{shipmentId}/events/{eventId}
	 * Method - DELETE
	 */
	@DeleteMapping("/{eventId}")
	public ResponseEntity<?> deleteEvent(@PathVariable Long shipmentId,
										@PathVariable Long eventId) {
		try {
			return ResponseEntity.ok(new ApiResponse(shipmentEventService.deleteEvent(eventId), "success"));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(e.getMessage(), "failed"));
		}
	}
}

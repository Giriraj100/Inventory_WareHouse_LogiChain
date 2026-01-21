package com.exam.service;

import java.sql.Timestamp;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.custom_exceptions.*;
import com.exam.dto.ShipmentDTO;
import com.exam.entities.*;
import com.exam.repository.*;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

	private final ShipmentRepository shipmentRepository;
	private final CarrierRepository carrierRepository;
	private final ShipmentEventRepository shipmentEventRepository;

	@Override
	public ShipmentDTO createShipment(ShipmentDTO dto) {

		if (shipmentRepository.existsByTrackingNumber(dto.getTrackingNumber()))
			throw new DuplicateResourceException("Tracking number already exists: " + dto.getTrackingNumber());

		if (shipmentRepository.existsByOrderId(dto.getOrderId()))
			throw new DuplicateResourceException("Shipment already exists for orderId: " + dto.getOrderId());

		Shipment s = new Shipment();
		s.setTrackingNumber(dto.getTrackingNumber());
		s.setOrderId(dto.getOrderId());
		s.setCurrentLocation(dto.getCurrentLocation());
		s.setEstimatedDeliveryDate(dto.getEstimatedDeliveryDate());
		s.setActualDeliveryDate(null);
		s.setShipmentStatus(dto.getShipmentStatus() == null ? ShipmentStatus.CREATED
				: ShipmentStatus.valueOf(dto.getShipmentStatus().toUpperCase()));
		s.setCreatedAt(new Timestamp(System.currentTimeMillis()));

		if (dto.getCarrierId() != null) {
			Carrier c = carrierRepository.findById(dto.getCarrierId())
					.orElseThrow(() -> new ResourceNotFoundException("Carrier not found with ID: " + dto.getCarrierId()));
			s.setCarrier(c);
		}

		Shipment saved = shipmentRepository.save(s);

		// auto create first event
		ShipmentEvent event = new ShipmentEvent();
		event.setShipment(saved);
		event.setEventType(ShipmentEventType.CREATED);
		event.setLocation(saved.getCurrentLocation());
		event.setDescription("Shipment created");
		event.setEventTimestamp(new Timestamp(System.currentTimeMillis()));
		event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		shipmentEventRepository.save(event);

		return toDTO(saved);
	}

	@Override
	public Page<ShipmentDTO> getAllShipments(Pageable pageable, ShipmentStatus status, Long orderId) {
		return shipmentRepository.fetchAllShipments(status, orderId, pageable).map(this::toDTO);
	}

	@Override
	public ShipmentDTO getShipmentById(Long id) {
		Shipment s = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + id));
		return toDTO(s);
	}

	@Override
	public ShipmentDTO getShipmentByTrackingNumber(String trackingNumber) {
		Shipment s = shipmentRepository.findByTrackingNumber(trackingNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with tracking number: " + trackingNumber));
		return toDTO(s);
	}

	@Override
	public ShipmentDTO getShipmentByOrderId(Long orderId) {
		Shipment s = shipmentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with orderId: " + orderId));
		return toDTO(s);
	}

	@Override
	public ShipmentDTO updateShipmentStatus(Long id, ShipmentStatus status, String location) {

		Shipment s = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + id));

		if (status == null)
			throw new InvalidOperationException("Status is required");

		s.setShipmentStatus(status);

		if (location != null)
			s.setCurrentLocation(location);

		// create event
		ShipmentEvent event = new ShipmentEvent();
		event.setShipment(s);
		event.setEventType(mapStatusToEvent(status));
		event.setLocation(s.getCurrentLocation());
		event.setDescription("Status updated to " + status.name());
		event.setEventTimestamp(new Timestamp(System.currentTimeMillis()));
		event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		shipmentEventRepository.save(event);

		return toDTO(s);
	}

	@Override
	public ShipmentDTO updateShipmentLocation(Long id, String location, Double latitude, Double longitude) {

		Shipment s = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + id));

		if (location == null || location.isBlank())
			throw new InvalidOperationException("Location is required");

		s.setCurrentLocation(location);

		ShipmentEvent event = new ShipmentEvent();
		event.setShipment(s);
		event.setEventType(ShipmentEventType.IN_TRANSIT);
		event.setLocation(location);
		event.setLatitude(latitude);
		event.setLongitude(longitude);
		event.setDescription("Location updated");
		event.setEventTimestamp(new Timestamp(System.currentTimeMillis()));
		event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		shipmentEventRepository.save(event);

		return toDTO(s);
	}

	@Override
	public ShipmentDTO markAsDelivered(Long id, String proofOfDeliveryUrl) {

		Shipment s = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + id));

		s.setShipmentStatus(ShipmentStatus.DELIVERED);
		s.setActualDeliveryDate(new Timestamp(System.currentTimeMillis()));

		ShipmentEvent event = new ShipmentEvent();
		event.setShipment(s);
		event.setEventType(ShipmentEventType.DELIVERED);
		event.setLocation(s.getCurrentLocation());
		event.setDescription("Delivered. Proof: " + proofOfDeliveryUrl);
		event.setEventTimestamp(new Timestamp(System.currentTimeMillis()));
		event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		shipmentEventRepository.save(event);

		return toDTO(s);
	}

	@Override
	public ShipmentDTO markAsFailed(Long id, String reason) {

		Shipment s = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + id));

		s.setShipmentStatus(ShipmentStatus.FAILED);

		ShipmentEvent event = new ShipmentEvent();
		event.setShipment(s);
		event.setEventType(ShipmentEventType.FAILED);
		event.setLocation(s.getCurrentLocation());
		event.setDescription("Failed. Reason: " + reason);
		event.setEventTimestamp(new Timestamp(System.currentTimeMillis()));
		event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		shipmentEventRepository.save(event);

		return toDTO(s);
	}

	@Override
	public ShipmentDTO cancelShipment(Long id, String reason) {

		Shipment s = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + id));

		// for demo: mark as FAILED on cancel
		s.setShipmentStatus(ShipmentStatus.FAILED);

		ShipmentEvent event = new ShipmentEvent();
		event.setShipment(s);
		event.setEventType(ShipmentEventType.FAILED);
		event.setLocation(s.getCurrentLocation());
		event.setDescription("Cancelled. Reason: " + reason);
		event.setEventTimestamp(new Timestamp(System.currentTimeMillis()));
		event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		shipmentEventRepository.save(event);

		return toDTO(s);
	}

	@Override
	public String deleteShipment(Long id) {
		Shipment s = shipmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + id));
		shipmentRepository.delete(s);
		return "Shipment deleted successfully";
	}

	private ShipmentDTO toDTO(Shipment s) {
		ShipmentDTO dto = new ShipmentDTO();
		dto.setId(s.getId());
		dto.setTrackingNumber(s.getTrackingNumber());
		dto.setOrderId(s.getOrderId());
		dto.setCarrierId(s.getCarrier() == null ? null : s.getCarrier().getId());
		dto.setShipmentStatus(s.getShipmentStatus().name());
		dto.setCurrentLocation(s.getCurrentLocation());
		dto.setEstimatedDeliveryDate(s.getEstimatedDeliveryDate());
		dto.setActualDeliveryDate(s.getActualDeliveryDate());
		dto.setCreatedAt(s.getCreatedAt());
		return dto;
	}

	private ShipmentEventType mapStatusToEvent(ShipmentStatus status) {
		return switch (status) {
			case CREATED -> ShipmentEventType.CREATED;
			case IN_TRANSIT -> ShipmentEventType.IN_TRANSIT;
			case DELIVERED -> ShipmentEventType.DELIVERED;
			case FAILED -> ShipmentEventType.FAILED;
		};
	}
}

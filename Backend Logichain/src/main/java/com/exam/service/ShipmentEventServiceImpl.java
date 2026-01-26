package com.exam.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.custom_exceptions.ResourceNotFoundException;
import com.exam.dto.ShipmentEventDTO;
import com.exam.entities.*;
import com.exam.repository.ShipmentEventRepository;
import com.exam.repository.ShipmentRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ShipmentEventServiceImpl implements ShipmentEventService {

	private final ShipmentEventRepository shipmentEventRepository;
	private final ShipmentRepository shipmentRepository;

	@Override
	public ShipmentEventDTO createEvent(Long shipmentId, ShipmentEventDTO dto) {
		return addManualEvent(shipmentId, dto);
	}

	@Override
	public Page<ShipmentEventDTO> getShipmentEvents(Long shipmentId, Pageable pageable) {
		return shipmentEventRepository.findByShipmentId(shipmentId, pageable)
				.map(this::toDTO);
	}

	@Override
	public ShipmentEventDTO getEventById(Long id) {
		ShipmentEvent e = shipmentEventRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment event not found with ID: " + id));
		return toDTO(e);
	}

	@Override
	public ShipmentEventDTO addManualEvent(Long shipmentId, ShipmentEventDTO dto) {

		Shipment shipment = shipmentRepository.findById(shipmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + shipmentId));

		ShipmentEvent event = new ShipmentEvent();
		event.setShipment(shipment);
		event.setEventType(ShipmentEventType.valueOf(dto.getEventType().toUpperCase()));
		event.setLocation(dto.getLocation());
		event.setLatitude(dto.getLatitude());
		event.setLongitude(dto.getLongitude());
		event.setDescription(dto.getDescription());
		event.setEventTimestamp(new Timestamp(System.currentTimeMillis()));
		event.setCreatedAt(new Timestamp(System.currentTimeMillis()));

		return toDTO(shipmentEventRepository.save(event));
	}

	@Override
	public ShipmentEventDTO getLatestEvent(Long shipmentId) {

		List<ShipmentEvent> list = shipmentEventRepository.findLatestByShipmentId(shipmentId, PageRequest.of(0, 1));

		if (list.isEmpty())
			throw new ResourceNotFoundException("No events found for shipmentId: " + shipmentId);

		return toDTO(list.get(0));
	}

	@Override
	public List<ShipmentEventDTO> getEventsByLocation(String location) {
		// Simple demo: fetch all and filter in memory
		return shipmentEventRepository.findAll()
				.stream()
				.filter(e -> e.getLocation() != null && e.getLocation().equalsIgnoreCase(location))
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public String deleteEvent(Long id) {
		ShipmentEvent e = shipmentEventRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Shipment event not found with ID: " + id));
		shipmentEventRepository.delete(e);
		return "Shipment event deleted successfully";
	}

	private ShipmentEventDTO toDTO(ShipmentEvent e) {
		ShipmentEventDTO dto = new ShipmentEventDTO();
		dto.setId(e.getId());
		dto.setShipmentId(e.getShipment().getId());
		dto.setEventType(e.getEventType().name());
		dto.setLocation(e.getLocation());
		dto.setLatitude(e.getLatitude());
		dto.setLongitude(e.getLongitude());
		dto.setDescription(e.getDescription());
		dto.setEventTimestamp(e.getEventTimestamp());
		dto.setCreatedAt(e.getCreatedAt());
		return dto;
	}
}

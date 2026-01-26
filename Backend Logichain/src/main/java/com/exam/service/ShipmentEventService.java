package com.exam.service;

import java.util.List;

import org.springframework.data.domain.*;

import com.exam.dto.ShipmentEventDTO;

public interface ShipmentEventService {

	ShipmentEventDTO createEvent(Long shipmentId, ShipmentEventDTO dto);

	Page<ShipmentEventDTO> getShipmentEvents(Long shipmentId, Pageable pageable);

	ShipmentEventDTO getEventById(Long id);

	ShipmentEventDTO addManualEvent(Long shipmentId, ShipmentEventDTO dto);

	ShipmentEventDTO getLatestEvent(Long shipmentId);

	List<ShipmentEventDTO> getEventsByLocation(String location);

	String deleteEvent(Long id);
}

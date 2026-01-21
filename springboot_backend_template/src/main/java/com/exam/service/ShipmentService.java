package com.exam.service;

import org.springframework.data.domain.*;

import com.exam.dto.ShipmentDTO;
import com.exam.entities.ShipmentStatus;

public interface ShipmentService {

	ShipmentDTO createShipment(ShipmentDTO dto);

	Page<ShipmentDTO> getAllShipments(Pageable pageable, ShipmentStatus status, Long orderId);

	ShipmentDTO getShipmentById(Long id);

	ShipmentDTO getShipmentByTrackingNumber(String trackingNumber);

	ShipmentDTO getShipmentByOrderId(Long orderId);

	ShipmentDTO updateShipmentStatus(Long id, ShipmentStatus status, String location);

	ShipmentDTO updateShipmentLocation(Long id, String location, Double latitude, Double longitude);

	ShipmentDTO markAsDelivered(Long id, String proofOfDeliveryUrl);

	ShipmentDTO markAsFailed(Long id, String reason);

	ShipmentDTO cancelShipment(Long id, String reason);

	String deleteShipment(Long id);
}

package com.exam.repository;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.exam.entities.*;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

	Optional<Shipment> findByTrackingNumber(String trackingNumber);

	Optional<Shipment> findByOrderId(Long orderId);

	boolean existsByTrackingNumber(String trackingNumber);

	boolean existsByOrderId(Long orderId);

	@Query("""
		select s from Shipment s
		where (:status is null or s.shipmentStatus = :status)
		and (:orderId is null or s.orderId = :orderId)
		""")
	Page<Shipment> fetchAllShipments(@Param("status") ShipmentStatus status,
									@Param("orderId") Long orderId,
									Pageable pageable);
}

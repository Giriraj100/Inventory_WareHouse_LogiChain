package com.exam.repository;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.exam.entities.ShipmentEvent;

public interface ShipmentEventRepository extends JpaRepository<ShipmentEvent, Long> {

	@Query("""
		select e from ShipmentEvent e
		where e.shipment.id = :shipmentId
		""")
	Page<ShipmentEvent> findByShipmentId(@Param("shipmentId") Long shipmentId, Pageable pageable);

	@Query("""
		select e from ShipmentEvent e
		where e.shipment.id = :shipmentId
		order by e.eventTimestamp desc
		""")
	List<ShipmentEvent> findLatestByShipmentId(@Param("shipmentId") Long shipmentId, Pageable pageable);
}

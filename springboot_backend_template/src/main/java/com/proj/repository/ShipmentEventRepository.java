package com.proj.repository;
import java.util.List;
import com.proj.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.entities.ShipmentEvent;
public interface ShipmentEventRepository 
extends JpaRepository<ShipmentEvent, Long> {

List<ShipmentEvent> findByShipmentId(Long shipmentId);
}

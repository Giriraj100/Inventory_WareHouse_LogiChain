package com.proj.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.entities.*;
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Shipment findByOrderId(Long orderId);
}

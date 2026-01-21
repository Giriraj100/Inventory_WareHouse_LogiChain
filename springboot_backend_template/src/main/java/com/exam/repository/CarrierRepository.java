package com.exam.repository;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entities.Carrier;

public interface CarrierRepository extends JpaRepository<Carrier, Long> {
	Optional<Carrier> findByCarrierCode(String carrierCode);
	boolean existsByCarrierCode(String carrierCode);
	Page<Carrier> findAll(Pageable pageable);
}


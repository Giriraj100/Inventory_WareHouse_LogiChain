package com.exam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entities.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
	Optional<Warehouse> findByCode(String code);
	boolean existsByCode(String code);
}

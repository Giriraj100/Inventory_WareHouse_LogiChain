package com.proj.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.entities.*;
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}

package com.proj.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.entities.Inventory;
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByProductId(Long productId);
    List<Inventory> findByWarehouseId(Long warehouseId);
}

package com.proj.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.entities.InventoryTransaction;
public interface InventoryTransactionRepository 
extends JpaRepository<InventoryTransaction, Long> {

List<InventoryTransaction> findByInventoryId(Long inventoryId);
}

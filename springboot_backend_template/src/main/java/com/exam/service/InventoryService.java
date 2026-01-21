package com.exam.service;

import java.util.List;

import org.springframework.data.domain.*;

import com.exam.dto.*;
import com.exam.entities.TransactionType;

public interface InventoryService {

	InventoryDTO addInventory(InventoryDTO dto);

	Page<InventoryDTO> getAllInventory(Pageable pageable, Long warehouseId, Long productId);

	InventoryDTO getInventoryById(Long id);

	InventoryDTO updateStockQuantity(Long id, Integer quantityChange, TransactionType type);

	InventoryDTO reserveStock(Long inventoryId, Integer quantity, Long orderId);

	InventoryDTO releaseStock(Long inventoryId, Integer quantity, Long orderId);

	List<InventoryDTO> getLowStockItems(Long warehouseId);

	String transferStock(Long productId, Long fromWarehouseId, Long toWarehouseId, Integer quantity);

	boolean checkStockAvailability(Long productId, Long warehouseId, Integer requiredQuantity);
}

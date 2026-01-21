package com.exam.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.custom_exceptions.*;
import com.exam.dto.InventoryDTO;
import com.exam.entities.*;
import com.exam.repository.*;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepository;
	private final ProductRepository productRepository;
	private final WarehouseRepository warehouseRepository;
	private final InventoryTransactionRepository inventoryTransactionRepository;

	@Override
	public InventoryDTO addInventory(InventoryDTO dto) {

		if (inventoryRepository.existsByProductIdAndWarehouseId(dto.getProductId(), dto.getWarehouseId()))
			throw new DuplicateResourceException("Inventory already exists for productId="
					+ dto.getProductId() + " and warehouseId=" + dto.getWarehouseId());

		Product product = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + dto.getProductId()));

		Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with ID: " + dto.getWarehouseId()));

		Inventory inv = new Inventory();
		inv.setProduct(product);
		inv.setWarehouse(warehouse);
		inv.setQuantity(dto.getQuantity() == null ? 0 : dto.getQuantity());
		inv.setReservedQuantity(dto.getReservedQuantity() == null ? 0 : dto.getReservedQuantity());
		inv.setCreatedAt(new Timestamp(System.currentTimeMillis()));

		return toDTO(inventoryRepository.save(inv));
	}

	@Override
	public Page<InventoryDTO> getAllInventory(Pageable pageable, Long warehouseId, Long productId) {
		return inventoryRepository.fetchAllInventory(warehouseId, productId, pageable)
				.map(this::toDTO);
	}

	@Override
	public InventoryDTO getInventoryById(Long id) {
		Inventory inv = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found with ID: " + id));
		return toDTO(inv);
	}

	@Override
	public InventoryDTO updateStockQuantity(Long id, Integer quantityChange, TransactionType type) {

		Inventory inv = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found with ID: " + id));

		if (quantityChange == null || quantityChange == 0)
			throw new InvalidOperationException("quantityChange must be non-zero");

		if (type == null)
			throw new InvalidOperationException("Transaction type is required");

		int newQty = inv.getQuantity();

		if (type == TransactionType.STOCK_IN) {
			newQty = newQty + quantityChange;
		} else if (type == TransactionType.STOCK_OUT) {
			if (newQty - quantityChange < 0)
				throw new InvalidOperationException("Stock cannot go negative");
			newQty = newQty - quantityChange;
		} else {
			throw new InvalidOperationException("Only STOCK_IN or STOCK_OUT allowed");
		}

		inv.setQuantity(newQty);

		InventoryTransaction tx = new InventoryTransaction();
		tx.setInventory(inv);
		tx.setTransactionType(type);
		tx.setQuantityChanged(quantityChange);
		tx.setReferenceId(null);
		tx.setTimestamp(new Timestamp(System.currentTimeMillis()));
		inventoryTransactionRepository.save(tx);

		return toDTO(inv);
	}

	@Override
	public InventoryDTO reserveStock(Long inventoryId, Integer quantity, Long orderId) {

		Inventory inv = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found with ID: " + inventoryId));

		if (quantity == null || quantity <= 0)
			throw new InvalidOperationException("Reserve quantity must be > 0");

		int available = inv.getQuantity() - inv.getReservedQuantity();
		if (available < quantity)
			throw new InvalidOperationException("Not enough stock available");

		inv.setReservedQuantity(inv.getReservedQuantity() + quantity);

		InventoryTransaction tx = new InventoryTransaction();
		tx.setInventory(inv);
		tx.setTransactionType(TransactionType.RESERVED);
		tx.setQuantityChanged(quantity);
		tx.setReferenceId(orderId);
		tx.setTimestamp(new Timestamp(System.currentTimeMillis()));
		inventoryTransactionRepository.save(tx);

		return toDTO(inv);
	}

	@Override
	public InventoryDTO releaseStock(Long inventoryId, Integer quantity, Long orderId) {

		Inventory inv = inventoryRepository.findById(inventoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found with ID: " + inventoryId));

		if (quantity == null || quantity <= 0)
			throw new InvalidOperationException("Release quantity must be > 0");

		if (inv.getReservedQuantity() < quantity)
			throw new InvalidOperationException("Cannot release more than reserved quantity");

		inv.setReservedQuantity(inv.getReservedQuantity() - quantity);

		InventoryTransaction tx = new InventoryTransaction();
		tx.setInventory(inv);
		tx.setTransactionType(TransactionType.RELEASED);
		tx.setQuantityChanged(quantity);
		tx.setReferenceId(orderId);
		tx.setTimestamp(new Timestamp(System.currentTimeMillis()));
		inventoryTransactionRepository.save(tx);

		return toDTO(inv);
	}

	@Override
	public List<InventoryDTO> getLowStockItems(Long warehouseId) {
		return inventoryRepository.findLowStockItems(warehouseId)
				.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public String transferStock(Long productId, Long fromWarehouseId, Long toWarehouseId, Integer quantity) {

		if (quantity == null || quantity <= 0)
			throw new InvalidOperationException("Transfer quantity must be > 0");

		Inventory fromInv = inventoryRepository.findByProductIdAndWarehouseId(productId, fromWarehouseId)
				.orElseThrow(() -> new ResourceNotFoundException("Source inventory not found"));

		Inventory toInv = inventoryRepository.findByProductIdAndWarehouseId(productId, toWarehouseId)
				.orElseThrow(() -> new ResourceNotFoundException("Destination inventory not found"));

		int available = fromInv.getQuantity() - fromInv.getReservedQuantity();
		if (available < quantity)
			throw new InvalidOperationException("Not enough available stock to transfer");

		fromInv.setQuantity(fromInv.getQuantity() - quantity);
		toInv.setQuantity(toInv.getQuantity() + quantity);

		return "Stock transferred successfully";
	}

	@Override
	public boolean checkStockAvailability(Long productId, Long warehouseId, Integer requiredQuantity) {

		if (requiredQuantity == null || requiredQuantity <= 0)
			throw new InvalidOperationException("requiredQuantity must be > 0");

		Inventory inv = inventoryRepository.findByProductIdAndWarehouseId(productId, warehouseId)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory not found for productId="
						+ productId + " and warehouseId=" + warehouseId));

		int available = inv.getQuantity() - inv.getReservedQuantity();
		return available >= requiredQuantity;
	}

	private InventoryDTO toDTO(Inventory inv) {
		InventoryDTO dto = new InventoryDTO();
		dto.setId(inv.getId());
		dto.setProductId(inv.getProduct().getId());
		dto.setWarehouseId(inv.getWarehouse().getId());
		dto.setQuantity(inv.getQuantity());
		dto.setReservedQuantity(inv.getReservedQuantity());
		dto.setCreatedAt(inv.getCreatedAt());
		return dto;
	}
}

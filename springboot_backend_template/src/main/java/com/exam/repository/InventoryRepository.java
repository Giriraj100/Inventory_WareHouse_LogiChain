package com.exam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.exam.entities.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Optional<Inventory> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

	boolean existsByProductIdAndWarehouseId(Long productId, Long warehouseId);

	@Query("""
		select i from Inventory i
		where (:warehouseId is null or i.warehouse.id = :warehouseId)
		and (:productId is null or i.product.id = :productId)
		""")
	Page<Inventory> fetchAllInventory(@Param("warehouseId") Long warehouseId,
									  @Param("productId") Long productId,
									  Pageable pageable);

	@Query("""
		select i from Inventory i
		where i.warehouse.id = :warehouseId
		and (i.quantity - i.reservedQuantity) <= 10
		""")
	List<Inventory> findLowStockItems(@Param("warehouseId") Long warehouseId);

	@Query("select sum(i.quantity) from Inventory i where i.product.id = :productId")
	Integer sumQuantityByProductId(@Param("productId") Long productId);

	@Query("select sum(i.reservedQuantity) from Inventory i where i.product.id = :productId")
	Integer sumReservedByProductId(@Param("productId") Long productId);

	@Query("select sum(i.quantity) from Inventory i where i.warehouse.id = :warehouseId")
	Integer sumQuantityByWarehouseId(@Param("warehouseId") Long warehouseId);
}

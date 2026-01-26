package com.exam.repository;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.exam.entities.InventoryTransaction;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {

	@Query("""
		select t from InventoryTransaction t
		where t.inventory.id = :inventoryId
		""")
	Page<InventoryTransaction> findByInventoryId(@Param("inventoryId") Long inventoryId, Pageable pageable);

	@Query("""
		select t from InventoryTransaction t
		where t.referenceId = :referenceId
		""")
	List<InventoryTransaction> findByReferenceId(@Param("referenceId") Long referenceId);
}

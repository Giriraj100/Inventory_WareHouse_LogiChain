package com.exam.service;

import org.springframework.data.domain.*;

import com.exam.dto.WarehouseCapacityDTO;
import com.exam.dto.WarehouseDTO;

public interface WarehouseService {

	WarehouseDTO createWarehouse(WarehouseDTO dto);

	Page<WarehouseDTO> getAllWarehouses(Pageable pageable);

	WarehouseDTO getWarehouseById(Long id);

	WarehouseDTO getWarehouseByCode(String code);

	WarehouseDTO updateWarehouse(Long id, WarehouseDTO dto);

	String deleteWarehouse(Long id);

	WarehouseCapacityDTO getWarehouseCapacity(Long id);
}

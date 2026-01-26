package com.exam.service;

import java.sql.Timestamp;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.custom_exceptions.*;
import com.exam.dto.WarehouseCapacityDTO;
import com.exam.dto.WarehouseDTO;
import com.exam.entities.Warehouse;
import com.exam.repository.InventoryRepository;
import com.exam.repository.WarehouseRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

	private final WarehouseRepository warehouseRepository;
	private final InventoryRepository inventoryRepository;
	private final ModelMapper modelMapper;

	@Override
	public WarehouseDTO createWarehouse(WarehouseDTO dto) {

		if (warehouseRepository.existsByCode(dto.getCode()))
			throw new DuplicateResourceException("Warehouse already exists with code: " + dto.getCode());

		Warehouse wh = modelMapper.map(dto, Warehouse.class);
		wh.setId(null);
		wh.setCreatedAt(new Timestamp(System.currentTimeMillis()));

		return modelMapper.map(warehouseRepository.save(wh), WarehouseDTO.class);
	}

	@Override
	public Page<WarehouseDTO> getAllWarehouses(Pageable pageable) {
		return warehouseRepository.findAll(pageable).map(w -> modelMapper.map(w, WarehouseDTO.class));
	}

	@Override
	public WarehouseDTO getWarehouseById(Long id) {
		Warehouse wh = warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with ID: " + id));
		return modelMapper.map(wh, WarehouseDTO.class);
	}

	@Override
	public WarehouseDTO getWarehouseByCode(String code) {
		Warehouse wh = warehouseRepository.findByCode(code)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with code: " + code));
		return modelMapper.map(wh, WarehouseDTO.class);
	}

	@Override
	public WarehouseDTO updateWarehouse(Long id, WarehouseDTO dto) {

		Warehouse wh = warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with ID: " + id));

		if (dto.getCode() != null && !dto.getCode().equals(wh.getCode())) {
			if (warehouseRepository.existsByCode(dto.getCode()))
				throw new DuplicateResourceException("Warehouse code already exists: " + dto.getCode());
		}

		modelMapper.map(dto, wh);

		return modelMapper.map(warehouseRepository.save(wh), WarehouseDTO.class);
	}

	@Override
	public String deleteWarehouse(Long id) {
		Warehouse wh = warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with ID: " + id));
		warehouseRepository.delete(wh);
		return "Warehouse deleted successfully";
	}

	@Override
	public WarehouseCapacityDTO getWarehouseCapacity(Long id) {

		Warehouse wh = warehouseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with ID: " + id));

		Integer totalStock = inventoryRepository.sumQuantityByWarehouseId(id);
		if (totalStock == null)
			totalStock = 0;

		Integer remaining = wh.getCapacity() == null ? 0 : (wh.getCapacity() - totalStock);

		return new WarehouseCapacityDTO(id, wh.getCapacity(), totalStock, remaining);
	}
}

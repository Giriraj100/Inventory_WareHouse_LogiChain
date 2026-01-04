package com.proj.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.proj.dto.InventoryDTO;
import com.proj.entities.Inventory;
import com.proj.repository.InventoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = modelMapper.map(inventory, InventoryDTO.class);
        dto.setProductId(inventory.getProduct().getId());
        dto.setWarehouseId(inventory.getWarehouse().getId());
        return dto;
    }
}

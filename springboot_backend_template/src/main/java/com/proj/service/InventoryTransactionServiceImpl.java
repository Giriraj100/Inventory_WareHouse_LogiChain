package com.proj.service;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.proj.dto.InventoryTransactionDTO;
import com.proj.repository.InventoryTransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class InventoryTransactionServiceImpl 
        implements InventoryTransactionService {

    private final InventoryTransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<InventoryTransactionDTO> getByInventoryId(Long inventoryId) {
        return transactionRepository.findByInventoryId(inventoryId)
                .stream()
                .map(t -> {
                    InventoryTransactionDTO dto =
                            modelMapper.map(t, InventoryTransactionDTO.class);
                    dto.setInventoryId(t.getInventory().getId());
                    return dto;
                })
                .toList();
    }
}

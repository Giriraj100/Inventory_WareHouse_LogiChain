package com.proj.controller;
import com.proj.service.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.dto.*;
@RestController
@RequestMapping("/api/inventory-transactions")
@RequiredArgsConstructor
public class InventoryTransactionController {

    private final InventoryTransactionService inventoryTransactionService;

    @GetMapping("/inventory/{inventoryId}")
    public List<InventoryTransactionDTO> getTransactionsByInventoryId(
            @PathVariable Long inventoryId) {

        return inventoryTransactionService.getByInventoryId(inventoryId);
    }
}

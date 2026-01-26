package com.proj.service;
import java.util.List;

import com.proj.dto.*;
public interface InventoryTransactionService {
    List<InventoryTransactionDTO> getByInventoryId(Long inventoryId);
}

package com.proj.dto;


import lombok.*;
import java.time.LocalDateTime;

import com.proj.entities.TransactionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryTransactionDTO {
    private Long id;
    private Long inventoryId;
    private TransactionType transactionType;
    private Integer quantityChanged;
    private Long referenceId;
    private LocalDateTime timestamp;
}
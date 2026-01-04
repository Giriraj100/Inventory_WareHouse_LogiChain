package com.proj.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory",
       uniqueConstraints = {
           @UniqueConstraint(
               name = "uk_inventory_product_warehouse",
               columnNames = {"product_id", "warehouse_id"}
           )
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_product"))
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_warehouse"))
    private Warehouse warehouse;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer reservedQuantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (quantity == null) {
            quantity = 0;
        }
        if (reservedQuantity == null) {
            reservedQuantity = 0;
        }
    }
}
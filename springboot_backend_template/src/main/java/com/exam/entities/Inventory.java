package com.exam.entities;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory",
		uniqueConstraints = @UniqueConstraint(columnNames = { "product_id", "warehouse_id" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_id", nullable = false)
	private Warehouse warehouse;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "reserved_quantity", nullable = false)
	private Integer reservedQuantity;

	@Column(name = "created_at")
	private Timestamp createdAt;
}

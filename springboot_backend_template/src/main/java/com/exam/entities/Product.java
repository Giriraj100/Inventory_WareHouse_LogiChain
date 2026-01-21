package com.exam.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products", uniqueConstraints = {
		@UniqueConstraint(columnNames = "sku")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String sku;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, precision = 38, scale = 2)
	private BigDecimal price;

	private Double weight;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(nullable = false)
	private Boolean isActive = true;
}

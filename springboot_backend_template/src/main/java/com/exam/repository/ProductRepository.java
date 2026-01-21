package com.exam.repository;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findBySku(String sku);

	boolean existsBySku(String sku);

	Page<Product> findByNameContainingIgnoreCase(String search, Pageable pageable);
}

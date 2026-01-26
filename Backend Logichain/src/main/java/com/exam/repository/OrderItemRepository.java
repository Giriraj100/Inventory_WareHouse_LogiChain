package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entities.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
}
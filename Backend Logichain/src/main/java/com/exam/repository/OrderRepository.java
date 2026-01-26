package com.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}
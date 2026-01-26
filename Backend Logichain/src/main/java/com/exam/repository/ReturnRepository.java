package com.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entities.Return;

import java.util.List;

public interface ReturnRepository extends JpaRepository<Return, Long> {
    List<Return> findByOrderId(Long orderId);
}
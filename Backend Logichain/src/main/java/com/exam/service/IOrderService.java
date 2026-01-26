package com.exam.service;

import java.util.List;

import com.exam.dto.OrderDTO;
import com.exam.dto.OrderRequestDTO;
import com.exam.entities.Order;

public interface IOrderService {
    List<OrderDTO> getAll();
    OrderDTO getById(Long id);
    List<OrderDTO> getByCustomerId(Long customerId);
    OrderDTO save(OrderRequestDTO order);
    void delete(Long id);
}
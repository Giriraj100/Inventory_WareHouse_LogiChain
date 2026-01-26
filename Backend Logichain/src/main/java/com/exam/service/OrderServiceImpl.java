package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.custom_exceptions.ResourceNotFoundException;
import com.exam.dto.OrderDTO;
import com.exam.dto.OrderRequestDTO;
import com.exam.entities.Order;
import com.exam.entities.Role;
import com.exam.entities.User;
import com.exam.repository.OrderRepository;
import com.exam.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository repo;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public List<OrderDTO> getAll() {
        return repo.findAll().stream()
                .map(e -> mapper.map(e, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getById(Long id) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return mapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getByCustomerId(Long customerId) {
        List<Order> orders = repo.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for customer id: " + customerId);
        }
        return orders.stream()
                .map(e -> mapper.map(e, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO save(OrderRequestDTO order) {
    	User u= userRepository.findById(order.getCustomerId()).orElseThrow(()->new ResourceNotFoundException("wrong user id"));
        
        Order o = mapper.map(order, Order.class);
        o.setCustomer(u);
        
        return mapper.map(repo.save(o), OrderDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
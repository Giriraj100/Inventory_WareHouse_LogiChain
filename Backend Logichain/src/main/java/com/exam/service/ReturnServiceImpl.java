package com.exam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.custom_exceptions.ResourceNotFoundException;
import com.exam.dto.OrderDTO;
import com.exam.dto.OrderRequestDTO;
import com.exam.dto.ReturnDTO;
import com.exam.entities.Order;
import com.exam.entities.Return;
import com.exam.entities.User;
import com.exam.repository.ReturnRepository;
import com.exam.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements IReturnService {
    private final ReturnRepository repo;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    public List<ReturnDTO> getAll() {
        return repo.findAll().stream()
                .map(e -> mapper.map(e, ReturnDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReturnDTO getById(Long id) {
        Return returnEntity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Return not found with id: " + id));
        return mapper.map(returnEntity, ReturnDTO.class);
    }
//    @Override
//    public OrderDTO save(OrderRequestDTO order) {
//    	User u= userRepository.findById(order.getCustomerId()).orElseThrow(()->new ResourceNotFoundException("wrong user id"));
//        
//        Order o = mapper.map(order, Order.class);
//        o.setCustomer(u);
//        
//        return mapper.map(repo.save(o), OrderDTO.class);
//    }
//    @Override
//    public OrderDTO save(ReturnRequestDTO order) {
//    	User u= userRepository.findById(order.getCustomerId()).orElseThrow(()->new ResourceNotFoundException("wrong user id"));
//        
//        Return o = mapper.map(order, Return.class);
//        o.setCustomer(u);
//        
//        return mapper.map(repo.save(o), ReturnDTO.class);
//    }
    @Override
    public ReturnDTO save(Return returnEntity) {
        return mapper.map(repo.save(returnEntity), ReturnDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Return not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
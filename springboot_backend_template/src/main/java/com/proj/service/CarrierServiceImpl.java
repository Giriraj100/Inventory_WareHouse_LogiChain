package com.proj.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.proj.dto.CarrierDTO;
import com.proj.entities.Carrier;
import com.proj.repository.CarrierRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CarrierServiceImpl implements CarrierService {

    private final CarrierRepository carrierRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CarrierDTO> getAllCarriers() {
        return carrierRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CarrierDTO.class))
                .toList();
    }

    @Override
    public CarrierDTO getCarrierById(Long id) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrier not found"));
        return modelMapper.map(carrier, CarrierDTO.class);
    }
}

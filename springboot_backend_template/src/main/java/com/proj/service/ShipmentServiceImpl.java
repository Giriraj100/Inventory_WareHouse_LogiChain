package com.proj.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.proj.dto.*;
import com.proj.entities.Shipment;
import com.proj.repository.ShipmentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ShipmentDTO> getAllShipments() {
        return shipmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private ShipmentDTO convertToDTO(Shipment shipment) {
        ShipmentDTO dto = modelMapper.map(shipment, ShipmentDTO.class);
        dto.setCarrierId(
            shipment.getCarrier() != null ? shipment.getCarrier().getId() : null
        );
        return dto;
    }
}

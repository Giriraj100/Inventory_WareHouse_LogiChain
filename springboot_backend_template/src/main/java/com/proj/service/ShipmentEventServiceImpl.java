package com.proj.service;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.proj.dto.ShipmentEventDTO;
import com.proj.repository.ShipmentEventRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentEventServiceImpl implements ShipmentEventService {

    private final ShipmentEventRepository eventRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ShipmentEventDTO> getEventsByShipmentId(Long shipmentId) {
        return eventRepository.findByShipmentId(shipmentId)
                .stream()
                .map(e -> {
                    ShipmentEventDTO dto =
                            modelMapper.map(e, ShipmentEventDTO.class);
                    dto.setShipmentId(e.getShipment().getId());
                    return dto;
                })
                .toList();
    }
}

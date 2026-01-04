package com.proj.controller;
import com.proj.service.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.dto.*;
@RestController
@RequestMapping("/api/shipment-events")
@RequiredArgsConstructor
public class ShipmentEventController {

    private final ShipmentEventService shipmentEventService;

    @GetMapping("/shipment/{shipmentId}")
    public List<ShipmentEventDTO> getEventsByShipmentId(
            @PathVariable Long shipmentId) {

        return shipmentEventService.getEventsByShipmentId(shipmentId);
    }
}

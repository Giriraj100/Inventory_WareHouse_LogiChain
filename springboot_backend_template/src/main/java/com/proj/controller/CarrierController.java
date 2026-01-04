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
@RequestMapping("/api/carriers")
@RequiredArgsConstructor
public class CarrierController {

    private final CarrierService carrierService;

    @GetMapping
    public List<CarrierDTO> getAllCarriers() {
        return carrierService.getAllCarriers();
    }

    @GetMapping("/{id}")
    public CarrierDTO getCarrierById(@PathVariable Long id) {
        return carrierService.getCarrierById(id);
    }
}

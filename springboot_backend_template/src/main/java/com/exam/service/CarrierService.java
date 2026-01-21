package com.exam.service;

import org.springframework.data.domain.*;

import com.exam.dto.CarrierDTO;

public interface CarrierService {

	CarrierDTO createCarrier(CarrierDTO dto);

	Page<CarrierDTO> getAllCarriers(Pageable pageable);

	CarrierDTO getCarrierById(Long id);

	CarrierDTO getCarrierByCode(String carrierCode);

	CarrierDTO updateCarrier(Long id, CarrierDTO dto);

	String deleteCarrier(Long id);

	CarrierDTO activateDeactivateCarrier(Long id, boolean isActive);
}

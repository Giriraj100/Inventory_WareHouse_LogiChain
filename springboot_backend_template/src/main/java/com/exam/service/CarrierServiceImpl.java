package com.exam.service;

import java.sql.Timestamp;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.custom_exceptions.*;
import com.exam.dto.CarrierDTO;
import com.exam.entities.Carrier;
import com.exam.repository.CarrierRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CarrierServiceImpl implements CarrierService {

	private final CarrierRepository carrierRepository;
	private final ModelMapper modelMapper;

	@Override
	public CarrierDTO createCarrier(CarrierDTO dto) {

		if (carrierRepository.existsByCarrierCode(dto.getCarrierCode()))
			throw new DuplicateResourceException("Carrier already exists with code: " + dto.getCarrierCode());

		Carrier carrier = modelMapper.map(dto, Carrier.class);
		carrier.setId(null);
		carrier.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		if (carrier.getIsActive() == null)
			carrier.setIsActive(true);

		return modelMapper.map(carrierRepository.save(carrier), CarrierDTO.class);
	}

	@Override
	public Page<CarrierDTO> getAllCarriers(Pageable pageable) {
		return carrierRepository.findAll(pageable).map(c -> modelMapper.map(c, CarrierDTO.class));
	}

	@Override
	public CarrierDTO getCarrierById(Long id) {
		Carrier c = carrierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Carrier not found with ID: " + id));
		return modelMapper.map(c, CarrierDTO.class);
	}

	@Override
	public CarrierDTO getCarrierByCode(String carrierCode) {
		Carrier c = carrierRepository.findByCarrierCode(carrierCode)
				.orElseThrow(() -> new ResourceNotFoundException("Carrier not found with code: " + carrierCode));
		return modelMapper.map(c, CarrierDTO.class);
	}

	@Override
	public CarrierDTO updateCarrier(Long id, CarrierDTO dto) {

		Carrier carrier = carrierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Carrier not found with ID: " + id));

		if (dto.getCarrierCode() != null && !dto.getCarrierCode().equals(carrier.getCarrierCode())) {
			if (carrierRepository.existsByCarrierCode(dto.getCarrierCode()))
				throw new DuplicateResourceException("Carrier code already exists: " + dto.getCarrierCode());
		}

		modelMapper.map(dto, carrier);
		return modelMapper.map(carrierRepository.save(carrier), CarrierDTO.class);
	}

	@Override
	public String deleteCarrier(Long id) {
		Carrier carrier = carrierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Carrier not found with ID: " + id));
		carrierRepository.delete(carrier);
		return "Carrier deleted successfully";
	}

	@Override
	public CarrierDTO activateDeactivateCarrier(Long id, boolean isActive) {
		Carrier carrier = carrierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Carrier not found with ID: " + id));
		carrier.setIsActive(isActive);
		return modelMapper.map(carrierRepository.save(carrier), CarrierDTO.class);
	}
}

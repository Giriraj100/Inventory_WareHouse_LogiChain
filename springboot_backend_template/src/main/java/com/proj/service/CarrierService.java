package com.proj.service;
import java.util.List;

import com.proj.dto.*;
public interface CarrierService {
    List<CarrierDTO> getAllCarriers();
    CarrierDTO getCarrierById(Long id);
}

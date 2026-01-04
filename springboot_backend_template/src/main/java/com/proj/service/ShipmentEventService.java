package com.proj.service;
import java.util.List;

import com.proj.dto.*;
public interface ShipmentEventService {
    List<ShipmentEventDTO> getEventsByShipmentId(Long shipmentId);
}

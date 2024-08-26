package com.saga.shipment.service;

import com.saga.choreography.dto.res.OrderResponseDto;
import com.saga.choreography.dto.res.ShipmentResponseDto;

public interface ShipmentService {
    ShipmentResponseDto placeShipment(final OrderResponseDto orderResponseDto);
    ShipmentResponseDto cancelShipment(final OrderResponseDto orderResponseDto);
}

package com.saga.shipment.service;

import com.saga.choreography.dto.res.OrderResponseDto;
import com.saga.choreography.dto.res.ShipmentResponseDto;
import com.saga.shipment.entity.Shipment;

import java.util.Optional;

public interface ShipmentService {
    ShipmentResponseDto placeShipment(final OrderResponseDto orderResponseDto);
    ShipmentResponseDto cancelShipment(final OrderResponseDto orderResponseDto);
    Optional<Shipment> fetchShipmentByOrderId(final String orderId);
}

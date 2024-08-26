package com.saga.shipment.service.impl;

import com.saga.choreography.dto.ShipmentStatus;
import com.saga.choreography.dto.res.OrderResponseDto;
import com.saga.choreography.dto.res.ShipmentResponseDto;
import com.saga.shipment.entity.Shipment;
import com.saga.shipment.repository.ShipmentRepository;
import com.saga.shipment.service.ShipmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ModelMapper modelMapper;
    private final ShipmentRepository shipmentRepository;

    @Override
    public ShipmentResponseDto placeShipment(OrderResponseDto orderResponseDto) {
        final var shipmentPlaced = shipmentRepository.fetchShipmentByOrderId(orderResponseDto.getId());
        if (shipmentPlaced.isPresent()) {
            log.warn("Shipment already placed with order id {}", orderResponseDto.getId());
            return modelMapper.map(shipmentPlaced, ShipmentResponseDto.class);
        }
        final Shipment shipment = new Shipment();
        shipment.setOrderId(orderResponseDto.getId());
        shipment.setStatus(ShipmentStatus.SUCCESS);
        return modelMapper.map(shipmentRepository.saveAndFlush(shipment), ShipmentResponseDto.class);
    }

    @Override
    public ShipmentResponseDto cancelShipment(OrderResponseDto orderResponseDto) {
        final var shipmentPlaced = shipmentRepository.fetchShipmentByOrderId(orderResponseDto.getId());
        if (shipmentPlaced.isEmpty()) {
            log.warn("Shipment not placed yet with order id {}", orderResponseDto.getId());
            return null;
        }
        shipmentPlaced.get().setStatus(ShipmentStatus.FAILED);
        return modelMapper.map(shipmentRepository.saveAndFlush(shipmentPlaced.get()), ShipmentResponseDto.class);
    }
}

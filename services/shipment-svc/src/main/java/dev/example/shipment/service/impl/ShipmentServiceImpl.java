package dev.example.shipment.service.impl;

import dev.example.shipment.entity.Shipment;
import dev.example.shipment.repository.ShipmentRepository;
import dev.example.shipment.service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    @Override
    public Shipment saveShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    @Override
    public List<Shipment> getShipmentsByOrderId(Long orderId) {
        return shipmentRepository.findAllShipmentByOrderId(orderId);
    }

    @Override
    public List<Shipment> saveAllAndFlush(List<Shipment> shipments) {
        return shipmentRepository.saveAllAndFlush(shipments);
    }
}

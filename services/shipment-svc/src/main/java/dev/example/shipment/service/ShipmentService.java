package dev.example.shipment.service;

import dev.example.shipment.entity.Shipment;

import java.util.List;

public interface ShipmentService {
    Shipment saveShipment(Shipment shipment);
    List<Shipment> getShipmentsByOrderId(Long orderId);
    List<Shipment> saveAllAndFlush(List<Shipment> shipments);
}

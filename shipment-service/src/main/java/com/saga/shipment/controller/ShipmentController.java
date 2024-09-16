package com.saga.shipment.controller;

import com.saga.shipment.service.ShipmentService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/shipment")
public class ShipmentController {
    private final ShipmentService shipmentService;


    @GetMapping("/{orderId}")
    public ResponseEntity<?> fetchShipmentDetailsByOrderId(@PathVariable("orderId") String orderId) {
        final var shipment = shipmentService.fetchShipmentByOrderId(orderId);
        return shipment.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(shipment.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found with order id " + orderId);
    }
}

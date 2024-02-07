package dev.example.shipment.workflow;

import dev.example.common.activities.ShipmentActivities;
import dev.example.common.model.OrderDTO;
import dev.example.common.model.Status;
import dev.example.shipment.entity.Shipment;
import dev.example.shipment.service.ShipmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ShipmentActivitiesImpl implements ShipmentActivities {
    private final ShipmentService shipmentService;
    @Override
    public void placeShipment(OrderDTO orderDTO) {
        log.info("Placing shipment");
        shipmentService.saveShipment(mapToEntity(orderDTO));
    }
    private Shipment mapToEntity(OrderDTO orderDTO){
        Shipment shipment=new Shipment();
        shipment.setOrderId(orderDTO.getOrderId());
        shipment.setStatus(Status.COMPLETED);
        return shipment;
    }

    @Override
    public void cancelShipment(OrderDTO orderDTO) {
        log.info("Canceling shipment");
        final var dbShipment= shipmentService.getShipmentsByOrderId(orderDTO.getOrderId());
        dbShipment.stream()
                .forEach(shipment -> {
                    shipment.setStatus(Status.FAILED);
                });
        shipmentService.saveAllAndFlush(dbShipment);
    }
}

package dev.example.common.activities;

import dev.example.common.model.OrderDTO;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ShipmentActivities {
    @ActivityMethod
    void placeShipment(final OrderDTO orderDTO);
    @ActivityMethod
    void cancelShipment(final OrderDTO orderDTO);
}

package dev.example.common.activities;

import dev.example.common.model.OrderDTO;
import io.temporal.activity.ActivityInterface;
import io.temporal.workflow.WorkflowMethod;

@ActivityInterface
public interface ShipmentActivities {
    @WorkflowMethod
    void placeShipment(final OrderDTO orderDTO);
    @WorkflowMethod
    void cancelShipment(final OrderDTO orderDTO);
}

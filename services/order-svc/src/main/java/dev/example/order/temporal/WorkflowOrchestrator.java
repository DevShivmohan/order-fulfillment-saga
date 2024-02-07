package dev.example.order.temporal;

import dev.example.common.model.OrderDTO;

public interface WorkflowOrchestrator {
    void createOrder(OrderDTO order);
}

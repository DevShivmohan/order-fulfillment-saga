package dev.example.order.workflow;

import dev.example.common.activities.OrderActivities;
import dev.example.common.model.OrderDTO;
import dev.example.common.model.Status;
import dev.example.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class OrderActivitiesImpl  implements OrderActivities {
    private final OrderRepository orderRepository;
    @Override
    public void completeOrder(OrderDTO order) {
        final var orderDB= orderRepository.findById(order.getOrderId())
                .orElseThrow(()->new RuntimeException("Order not found with id "+order.getOrderId()));
        orderDB.setStatus(Status.COMPLETED);
        orderRepository.save(orderDB);
    }

    @Override
    public void failOrder(OrderDTO order) {
        final var orderDB= orderRepository.findById(order.getOrderId())
                .orElseThrow(()->new RuntimeException("Order not found with id "+order.getOrderId()));
        orderDB.setStatus(Status.FAILED);
        orderRepository.save(orderDB);
    }
}

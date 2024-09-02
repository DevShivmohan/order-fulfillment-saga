package dev.example.order.workflow;

import dev.example.common.activities.OrderActivities;
import dev.example.common.model.OrderDTO;
import dev.example.common.model.Status;
import dev.example.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@AllArgsConstructor
public class OrderActivitiesImpl  implements OrderActivities {
    private final OrderService orderService;

    @Override
    @Transactional
    public void initiateOrder(OrderDTO orderDTO) {
        // to be implemented, right now it's initiated at the controller layer
    }

    @Override
    @Transactional
    public void completeOrder(OrderDTO order) {
        log.info("Completing the order");
        final var orderDB= orderService.getOrder(order.getOrderId());
        orderDB.setStatus(Status.COMPLETED);
        orderService.saveOrder(orderDB);
    }

    @Transactional
    @Override
    public void failOrder(OrderDTO order) {
        log.info("Failing the order");
        final var orderDB= orderService.getOrder(order.getOrderId());
        orderDB.setStatus(Status.FAILED);
        orderService.saveOrder(orderDB);
    }
}

package dev.example.order.controller;

import dev.example.common.model.OrderDTO;
import dev.example.common.model.Status;
import dev.example.order.entity.Order;
import dev.example.order.service.OrderService;
import dev.example.order.temporal.WorkflowOrchestrator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final WorkflowOrchestrator workflowOrchestrator;
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO){
        final var dbOrder= orderService.saveOrder(mapToEntity(orderDTO));
        log.info("Order initiated "+dbOrder);
        orderDTO.setOrderId(dbOrder.getId());
        orderDTO.setStatus(dbOrder.getStatus());
        workflowOrchestrator.createOrder(orderDTO);
        log.info("Worker started");
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @GetMapping
    public ResponseEntity<?> getOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(id));
    }

    private Order mapToEntity(final OrderDTO orderDTO){
        Order order=new Order();
        order.setProductName(orderDTO.getProductName());
        order.setProductId(orderDTO.getProductId());
        order.setPrice(orderDTO.getPrice());
        order.setStatus(Status.INITIATED);
        return order;
    }
}

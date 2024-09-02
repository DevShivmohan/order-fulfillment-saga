package dev.example.order.controller;

import dev.example.common.model.OrderDTO;
import dev.example.common.model.Status;
import dev.example.order.dto.OrderRequestDto;
import dev.example.order.entity.Order;
import dev.example.order.service.OrderService;
import dev.example.order.temporal.WorkflowOrchestrator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final WorkflowOrchestrator workflowOrchestrator;
    @PostMapping
    @Transactional
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto orderRequestDto){
        final var dbOrder= orderService.saveOrder(mapToEntity(orderRequestDto));
        log.info("Order initiated {}",dbOrder);
        final var orderDTO = mapToOrderDto(dbOrder);
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

    private Order mapToEntity(final OrderRequestDto orderDTO){
        final Order order=new Order();
        order.setProductName(orderDTO.getProductName());
        order.setProductId(orderDTO.getProductId());
        order.setPrice(orderDTO.getPrice());
        order.setStatus(Status.INITIATED);
        return order;
    }

    private OrderDTO mapToOrderDto(final Order order){
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductName(order.getProductName());
        orderDTO.setProductId(order.getProductId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setOrderId(order.getId());
        return orderDTO;
    }
}

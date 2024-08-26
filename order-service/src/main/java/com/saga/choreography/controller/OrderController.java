package com.saga.choreography.controller;

import com.saga.choreography.dto.req.OrderRequestDto;
import com.saga.choreography.event.KafkaEventType;
import com.saga.choreography.kafka.KafkaEventPublisher;
import com.saga.choreography.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.saga.choreography.constants.KafkaEventConstants.KAFKA_TOPIC_ORDER_INITIATED;

@RestController
@AllArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final KafkaEventPublisher kafkaEventPublisher;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody final OrderRequestDto orderRequestDto) {
        final var order = orderService.createOrder(orderRequestDto);
        try {
            kafkaEventPublisher.publishKafkaEvent(KAFKA_TOPIC_ORDER_INITIATED, KafkaEventType.ORDER_INITIATED, order);
        } catch (Exception e) {
            orderService.failOrder(order);
            log.error(e.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }
}

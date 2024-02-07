package dev.example.order.controller;

import dev.example.common.Worker;
import dev.example.common.model.OrderDTO;
import dev.example.common.model.Status;
import dev.example.order.entity.Order;
import dev.example.order.repository.OrderRepository;
import dev.example.common.workflow.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO){
        final var workflowClient= Worker.getWorkflowClient();
        final WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Worker.ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE)
                .build();
        final OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, options);
        final var dbOrder=orderRepository.save(mapToEntity(orderDTO));
        orderDTO.setOrderId(dbOrder.getId());
        WorkflowClient.start(workflow::processOrder,orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @GetMapping
    public ResponseEntity<?> getOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findById(id).get());
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

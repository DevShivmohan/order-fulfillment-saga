package dev.example.order.temporal.impl;

import dev.example.common.TaskQueue;
import dev.example.common.model.OrderDTO;
import dev.example.common.workflow.OrderWorkflow;
import dev.example.order.properties.ApplicationProperties;
import dev.example.order.temporal.WorkflowOrchestrator;
import dev.example.order.temporal.WorkflowOrchestratorClient;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@EnableConfigurationProperties(ApplicationProperties.class)
public class WorkflowOrchestratorImpl implements WorkflowOrchestrator {
    private final WorkflowOrchestratorClient workflowOrchestratorClient;
    private final ApplicationProperties applicationProperties;
    @Override
    public void createOrder(OrderDTO order) {
        var workflowClient = workflowOrchestratorClient.getWorkflowClient();
        var orderFulfillmentWorkflow =
                workflowClient.newWorkflowStub(
                        OrderWorkflow.class,
                        WorkflowOptions.newBuilder()
                                .setWorkflowId(applicationProperties.getWorkflowId()+order.getOrderId())
                                .setTaskQueue(TaskQueue.ORDER_FULFILLMENT_WORKFLOW_TASK_QUEUE.name())
                                .build());
        // Execute Sync
        //    orderFulfillmentWorkflow.createOrder(orderDTO);
        // Async execution
        WorkflowClient.start(orderFulfillmentWorkflow::processOrder, order);
    }
}

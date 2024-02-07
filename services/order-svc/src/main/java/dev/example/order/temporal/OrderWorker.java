package dev.example.order.temporal;

import dev.example.common.TaskQueue;
import dev.example.common.activities.OrderActivities;
import dev.example.common.workflow.impl.OrderWorkflowImpl;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
@AllArgsConstructor
public class OrderWorker {
    private final OrderActivities orderActivity;
    private final WorkflowOrchestratorClient workflowOrchestratorClient;

    @PostConstruct
    public void startWorkers() {
        var workerOptions = WorkerOptions.newBuilder().build();
        var workflowClient = workflowOrchestratorClient.getWorkflowClient();
        WorkflowImplementationOptions workflowImplementationOptions =
                WorkflowImplementationOptions.newBuilder()
                        .setFailWorkflowExceptionTypes(NullPointerException.class)
                        .build();
        var workerFactory = WorkerFactory.newInstance(workflowClient);
        var worker =
                workerFactory.newWorker(
                        TaskQueue.ORDER_FULFILLMENT_WORKFLOW_TASK_QUEUE.name(), workerOptions);
        // Can be called multiple times
        worker.registerWorkflowImplementationTypes(
                workflowImplementationOptions, OrderWorkflowImpl.class);

        worker.registerActivitiesImplementations(orderActivity);

        workerFactory.start();

        log.info("Registering order worker..");
    }
}

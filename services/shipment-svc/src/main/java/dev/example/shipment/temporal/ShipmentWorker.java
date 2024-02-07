package dev.example.shipment.temporal;

import dev.example.common.TaskQueue;
import dev.example.common.activities.ShipmentActivities;
import io.temporal.worker.WorkerFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
@AllArgsConstructor
public class ShipmentWorker {
    private final ShipmentActivities shipmentActivities;
    private final WorkflowOrchestratorClient workflowOrchestratorClient;

    @PostConstruct
    public void createWorker() {

        log.info("Registering worker..");

        final var workflowClient = workflowOrchestratorClient.getWorkflowClient();

        final var workerFactory = WorkerFactory.newInstance(workflowClient);
        final var worker = workerFactory.newWorker(TaskQueue.SHIPPING_ACTIVITY_TASK_QUEUE.name());

        worker.registerActivitiesImplementations(shipmentActivities);

        workerFactory.start();

        log.info("Shipment worker started..");
    }
}

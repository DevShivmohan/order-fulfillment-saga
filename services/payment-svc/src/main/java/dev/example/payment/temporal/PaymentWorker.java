package dev.example.payment.temporal;

import dev.example.common.TaskQueue;
import dev.example.common.activities.PaymentActivities;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@Component
public class PaymentWorker {
    private final PaymentActivities paymentActivities;
    private final WorkflowOrchestratorClient workflowOrchestratorClient;
    @PostConstruct
    public void createWorker() {

        log.info("Registering Payment worker..");

        final var workflowClient = workflowOrchestratorClient.getWorkflowClient();

        final var workerOptions = WorkerOptions.newBuilder().build();

        final var workerFactory = WorkerFactory.newInstance(workflowClient);
        final var worker =
                workerFactory.newWorker(TaskQueue.PAYMENT_ACTIVITY_TASK_QUEUE.name(), workerOptions);

        worker.registerActivitiesImplementations(paymentActivities);

        workerFactory.start();

        log.info("Registered Payment worker..");
    }
}

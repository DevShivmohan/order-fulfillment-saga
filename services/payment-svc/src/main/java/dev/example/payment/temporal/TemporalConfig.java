package dev.example.payment.temporal;

import dev.example.common.activities.PaymentActivities;
import dev.example.common.workflow.OrderWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class TemporalConfig {
    @Autowired
    private PaymentActivities paymentActivities;

    @PostConstruct
    public void startWorkers() {
        var stub = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setEnableHttps(false)
                .setTarget("127.0.0.1:7233")
                .build());
        var client = WorkflowClient.newInstance(stub);
        var factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(dev.example.common.Worker.ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class);
        worker.registerActivitiesImplementations(paymentActivities);
        factory.start();
    }
}

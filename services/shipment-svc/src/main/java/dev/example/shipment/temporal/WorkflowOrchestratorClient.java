package dev.example.shipment.temporal;

import dev.example.shipment.properties.ApplicationProperties;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@EnableConfigurationProperties(ApplicationProperties.class)
public class WorkflowOrchestratorClient {
    private final ApplicationProperties applicationProperties;
    public WorkflowClient getWorkflowClient() {
        final var workflowServiceStubsOptions =
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(applicationProperties.getTemporalServer())
                        .build();
        final var workflowServiceStubs = WorkflowServiceStubs.newServiceStubs(workflowServiceStubsOptions);
        return WorkflowClient.newInstance(workflowServiceStubs);
    }
}

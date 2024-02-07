package dev.example.order.temporal;

import dev.example.order.properties.ApplicationProperties;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(ApplicationProperties.class)
@AllArgsConstructor
public class WorkflowOrchestratorClient {
    private final ApplicationProperties applicationProperties;
    public WorkflowClient getWorkflowClient() {
        var workflowServiceStubsOptions =
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(applicationProperties.getTemporalServer())
                        .build();
        var workflowServiceStubs = WorkflowServiceStubs.newServiceStubs(workflowServiceStubsOptions);
        return WorkflowClient.newInstance(workflowServiceStubs);
    }
}

package dev.example.common;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

public class Worker {

    public static final String ORDER_LIFECYCLE_WORKFLOW_TASK_QUEUE = "OrderLifecycleWorkflowTaskQueue";
    public static WorkflowClient getWorkflowClient() {
        final WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .setEnableHttps(false)
                .setTarget("127.0.0.1:7233")
                .build());
        return WorkflowClient.newInstance(service);
    }
}

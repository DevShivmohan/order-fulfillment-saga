package dev.example.common.workflow;

import dev.example.common.TaskQueue;
import dev.example.common.activities.OrderActivities;
import dev.example.common.activities.PaymentActivities;
import dev.example.common.activities.ShipmentActivities;
import dev.example.common.model.OrderDTO;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
//@Component
public class OrderWorkflowImpl implements OrderWorkflow {
    private final ActivityOptions paymentActivityOptions =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(1))
                    .setTaskQueue(TaskQueue.PAYMENT_ACTIVITY_TASK_QUEUE.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();
    private final ActivityOptions shippingActivityOptions =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(1))
                    .setTaskQueue(TaskQueue.SHIPPING_ACTIVITY_TASK_QUEUE.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();

    private final LocalActivityOptions localActivityOptions =
            LocalActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(1))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(10).build())
                    .build();
    private final OrderActivities orderActivities =
            Workflow.newLocalActivityStub(OrderActivities.class, localActivityOptions);

    private final PaymentActivities paymentActivities =
            Workflow.newActivityStub(PaymentActivities.class, paymentActivityOptions);
    private final ShipmentActivities shipmentActivities=Workflow.newActivityStub(ShipmentActivities.class,shippingActivityOptions);

    @Override
    public void processOrder(final OrderDTO orderDTO) {
        // Configure SAGA to run compensation activities in parallel
        Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(true).build();
        Saga saga = new Saga(sagaOptions);
        try {
            paymentActivities.debitPayment(orderDTO);
            saga.addCompensation(paymentActivities::reversePayment,orderDTO);
            shipmentActivities.placeShipment(orderDTO);
            saga.addCompensation(shipmentActivities::cancelShipment,orderDTO);
            orderActivities.completeOrder(orderDTO);
            saga.addCompensation(orderActivities::failOrder,orderDTO);
        }catch (ActivityFailure activityFailure){
            saga.compensate();
            throw activityFailure;
        }
    }
}

package dev.example.common.workflow.impl;

import dev.example.common.TaskQueue;
import dev.example.common.activities.OrderActivities;
import dev.example.common.activities.PaymentActivities;
import dev.example.common.activities.ShipmentActivities;
import dev.example.common.model.OrderDTO;
import dev.example.common.workflow.OrderWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
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

    private final LocalActivityOptions orderActivityOptions =
            LocalActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(1))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();
    private final OrderActivities orderActivities =
            Workflow.newLocalActivityStub(OrderActivities.class, orderActivityOptions);

    private final PaymentActivities paymentActivities =
            Workflow.newActivityStub(PaymentActivities.class, paymentActivityOptions);
    private final ShipmentActivities shipmentActivities=Workflow.newActivityStub(ShipmentActivities.class,shippingActivityOptions);

    @Override
    public void processOrder(final OrderDTO orderDTO) {
        // Configure SAGA to run compensation activities in parallel
        log.info("Workflow initiated");
        final Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(true).build();
        final Saga saga = new Saga(sagaOptions);
        try {
            log.info("Order initiated");
            saga.addCompensation(orderActivities::failOrder,orderDTO);
            orderActivities.initiateOrder(orderDTO);
            log.info("Debit payment initiated");
            saga.addCompensation(paymentActivities::reversePayment,orderDTO);
            paymentActivities.debitPayment(orderDTO);
            log.info("Shipment initiated");
            saga.addCompensation(shipmentActivities::cancelShipment,orderDTO);
            shipmentActivities.placeShipment(orderDTO);
            log.info("Order placing initiated");
            saga.addCompensation(orderActivities::failOrder,orderDTO);
            orderActivities.completeOrder(orderDTO);
            log.info("All activities completed");
        }catch (ActivityFailure activityFailure){
            log.error("Activity failure {}",activityFailure);
            saga.compensate();
            throw activityFailure;
        }
    }
}

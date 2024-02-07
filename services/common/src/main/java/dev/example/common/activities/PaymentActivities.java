package dev.example.common.activities;

import dev.example.common.model.OrderDTO;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentActivities {
  @ActivityMethod
  void debitPayment(OrderDTO orderDTO);
  @ActivityMethod
  void reversePayment(OrderDTO orderDTO);
}

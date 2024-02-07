package dev.example.common.activities;

import dev.example.common.model.OrderDTO;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivities {
  @ActivityMethod
  void initiateOrder(OrderDTO orderDTO);
  @ActivityMethod
  void completeOrder(OrderDTO order);
  @ActivityMethod
  void failOrder(OrderDTO orderDTO);
}

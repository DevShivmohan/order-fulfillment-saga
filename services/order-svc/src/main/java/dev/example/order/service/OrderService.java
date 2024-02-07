package dev.example.order.service;

import dev.example.order.entity.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    Order getOrder(Long id);
    List<Order> getOrders();
}

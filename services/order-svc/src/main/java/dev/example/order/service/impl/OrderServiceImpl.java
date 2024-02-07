package dev.example.order.service.impl;

import dev.example.order.entity.Order;
import dev.example.order.repository.OrderRepository;
import dev.example.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Order not found with id "+id));
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}

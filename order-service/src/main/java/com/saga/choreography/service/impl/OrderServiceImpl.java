package com.saga.choreography.service.impl;

import com.saga.choreography.dto.OrderStatus;
import com.saga.choreography.dto.req.OrderRequestDto;
import com.saga.choreography.dto.res.OrderResponseDto;
import com.saga.choreography.entity.Order;
import com.saga.choreography.repository.OrderRepository;
import com.saga.choreography.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        final Order order = modelMapper.map(orderRequestDto, Order.class);
        order.setStatus(OrderStatus.PENDING);
        return modelMapper.map(orderRepository.saveAndFlush(order), OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto failOrder(OrderResponseDto orderResponseDto) {
        final Order order = orderRepository.findById(orderResponseDto.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.FAILED);
        return modelMapper.map(orderRepository.saveAndFlush(order), OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto completeOrder(OrderResponseDto orderResponseDto) {
        final Order order = orderRepository.findById(orderResponseDto.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.SUCCESS);
        return modelMapper.map(orderRepository.saveAndFlush(order), OrderResponseDto.class);
    }

    @Override
    public OrderResponseDto getOrderById(String id) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderResponseDto.class);
    }
}

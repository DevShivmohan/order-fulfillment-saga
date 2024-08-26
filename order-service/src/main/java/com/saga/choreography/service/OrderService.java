package com.saga.choreography.service;


import com.saga.choreography.dto.req.OrderRequestDto;
import com.saga.choreography.dto.res.OrderResponseDto;

public interface OrderService {
    OrderResponseDto createOrder(final OrderRequestDto orderRequestDto);
    OrderResponseDto failOrder(final OrderResponseDto orderResponseDto);
    OrderResponseDto completeOrder(final OrderResponseDto orderResponseDto);
    OrderResponseDto getOrderById(final String id);
}

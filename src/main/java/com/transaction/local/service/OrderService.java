package com.transaction.local.service;

import com.transaction.local.dto.OrderRequestDto;
import com.transaction.local.entity.Order;

public interface OrderService {
    Order placeOrder(final OrderRequestDto orderRequestDto);
}

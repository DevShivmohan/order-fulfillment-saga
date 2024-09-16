package com.transaction.local.service.impl;

import com.transaction.local.dto.OrderRequestDto;
import com.transaction.local.entity.Order;
import com.transaction.local.entity.Payment;
import com.transaction.local.repository.OrderRepository;
import com.transaction.local.repository.PaymentRepository;
import com.transaction.local.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Log4j2
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public Order placeOrder(OrderRequestDto orderRequestDto) {
        final Order order = new Order();
        order.setItemName(orderRequestDto.getItemName());
        order.setAmount(orderRequestDto.getAmount());
        orderRepository.saveAndFlush(order);
        final Payment payment = new Payment();
        payment.setAmount(order.getAmount());
        payment.setOrderId(order.getId());
        paymentRepository.saveAndFlush(payment);
        return order;
    }
}

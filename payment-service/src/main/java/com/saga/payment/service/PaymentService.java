package com.saga.payment.service;

import com.saga.choreography.dto.res.OrderResponseDto;
import com.saga.choreography.dto.res.PaymentResponseDto;
import com.saga.payment.entity.Payment;

import java.util.Optional;

public interface PaymentService {
    PaymentResponseDto makePayment(final OrderResponseDto orderResponseDto);
    PaymentResponseDto reversePayment(final OrderResponseDto orderResponseDto);
    Optional<Payment> fetchPaymentByOrderId(final String orderId);
}

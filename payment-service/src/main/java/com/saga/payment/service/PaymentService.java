package com.saga.payment.service;

import com.saga.choreography.dto.res.OrderResponseDto;
import com.saga.choreography.dto.res.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto makePayment(final OrderResponseDto orderResponseDto);
    PaymentResponseDto reversePayment(final OrderResponseDto orderResponseDto);
}

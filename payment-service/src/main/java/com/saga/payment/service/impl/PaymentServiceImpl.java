package com.saga.payment.service.impl;

import com.saga.choreography.dto.PaymentStatus;
import com.saga.choreography.dto.res.OrderResponseDto;
import com.saga.choreography.dto.res.PaymentResponseDto;
import com.saga.payment.entity.Payment;
import com.saga.payment.repository.PaymentRepository;
import com.saga.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDto makePayment(OrderResponseDto orderResponseDto) {
        final var paymentDB = paymentRepository.findPaymentByOrderId(orderResponseDto.getId());
        if (paymentDB.isPresent()) {
            log.warn("Payment already made with order id {}", orderResponseDto.getId());
            return modelMapper.map(paymentDB, PaymentResponseDto.class);
        }
        log.info("Making the payment of order id {} with amount {}", orderResponseDto.getId(), orderResponseDto.getAmount());
        final Payment payment = new Payment();
        payment.setOrderId(orderResponseDto.getId());
        payment.setStatus(PaymentStatus.DEBIT);
        payment.setAmount(orderResponseDto.getAmount());
        return modelMapper.map(paymentRepository.saveAndFlush(payment), PaymentResponseDto.class);
    }

    @Override
    public PaymentResponseDto reversePayment(OrderResponseDto orderResponseDto) {
        final var debitedPayment = paymentRepository.findPaymentByOrderId(orderResponseDto.getId());
        if (debitedPayment.isEmpty()) {
            log.warn("Aborting of the payment failed due to payment not made");
            return null;
        }
        log.info("Rolling back the payment of order id {} with amount {}", orderResponseDto.getId(), orderResponseDto.getAmount());
        debitedPayment.get().setStatus(PaymentStatus.CREDIT);
        return modelMapper.map(paymentRepository.saveAndFlush(debitedPayment.get()), PaymentResponseDto.class);
    }
}

package dev.example.payment.service;

import dev.example.payment.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment savePayment(Payment payment);
    List<Payment> getPaymentsByOrderId(Long orderId);
    List<Payment> saveAllAndFlush(List<Payment> payments);
}

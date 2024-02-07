package dev.example.payment.service.impl;

import dev.example.payment.entity.Payment;
import dev.example.payment.repository.PaymentRepository;
import dev.example.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findAllByOrderId(orderId);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<Payment> saveAllAndFlush(List<Payment> payments) {
        return paymentRepository.saveAllAndFlush(payments);
    }
}

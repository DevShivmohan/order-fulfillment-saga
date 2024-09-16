package com.saga.payment.controller;

import com.saga.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;


    @GetMapping("/{orderId}")
    public ResponseEntity<?> fetchPaymentDetailsByOrderId(@PathVariable("orderId") String orderId) {
        final var payment = paymentService.fetchPaymentByOrderId(orderId);
        return payment.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(payment.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found with order id " + orderId);
    }
}

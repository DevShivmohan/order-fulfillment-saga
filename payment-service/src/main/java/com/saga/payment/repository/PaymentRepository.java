package com.saga.payment.repository;

import com.saga.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,String> {
    @Query(nativeQuery = true,value = "select * from payment where order_id=?1 AND status='DEBIT'")
    Optional<Payment> findPaymentByOrderIdWithDebitStatus(String orderId);

    @Query(nativeQuery = true,value = "select * from payment where order_id=?1 AND status='CREDIT'")
    Optional<Payment> findPaymentByOrderIdWithCreditStatus(String orderId);

    @Query(nativeQuery = true,value = "select * from payment where order_id=?1")
    Optional<Payment> findPaymentByOrderId(String orderId);
}

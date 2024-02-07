package dev.example.payment.repository;

import dev.example.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Query(value = "select * from payment where order_id=?1",nativeQuery = true)
    List<Payment> findAllByOrderId(Long orderId);
}

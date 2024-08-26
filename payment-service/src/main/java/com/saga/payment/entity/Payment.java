package com.saga.payment.entity;

import com.saga.choreography.dto.PaymentStatus;
import com.saga.choreography.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment extends BaseEntity {
    @Column(nullable = false)
    private String orderId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}

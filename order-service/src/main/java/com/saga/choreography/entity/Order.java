package com.saga.choreography.entity;

import com.saga.choreography.dto.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity{

    private String itemName;

    private Double amount;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

}

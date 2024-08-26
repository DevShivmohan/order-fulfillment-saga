package com.saga.shipment.entity;

import com.saga.choreography.dto.ShipmentStatus;
import com.saga.choreography.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shipment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipment extends BaseEntity {
    private String orderId;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
}

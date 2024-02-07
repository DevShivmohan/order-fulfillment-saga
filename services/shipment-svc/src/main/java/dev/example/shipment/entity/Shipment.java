package dev.example.shipment.entity;

import dev.example.common.model.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "shipment")
@Data
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long orderId;
    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "ENUM('FAILED','COMPLETED') DEFAULT 'COMPLETED'",nullable = false)
    private Status status;
    void persist(){
        this.id=0L;
    }
}

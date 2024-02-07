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
    private Status status;
    void persist(){
        this.id=0L;
    }
}

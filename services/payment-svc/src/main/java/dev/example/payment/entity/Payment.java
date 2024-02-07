package dev.example.payment.entity;

import dev.example.common.model.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private Double price;
    private Status status;
    private OperationCode operationCode;

    void persist(){
        this.id=0L;
    }
}

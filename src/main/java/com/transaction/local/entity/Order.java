package com.transaction.local.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    private String itemName;

    private Double amount;
}

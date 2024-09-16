package com.transaction.local.dto;

import lombok.Data;

@Data
public class OrderRequestDto {

    private String itemName;

    private Double amount;
}

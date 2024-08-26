package com.saga.choreography.dto.req;

import lombok.Data;

@Data
public class OrderRequestDto {
    private String itemName;
    private Double amount;
}

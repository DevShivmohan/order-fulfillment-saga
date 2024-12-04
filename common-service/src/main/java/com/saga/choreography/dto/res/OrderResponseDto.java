package com.saga.choreography.dto.res;

import com.saga.choreography.dto.OrderStatus;
import com.saga.choreography.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderResponseDto extends BaseEntity {
    private String itemName;

    private Double amount;

    private OrderStatus status;
}
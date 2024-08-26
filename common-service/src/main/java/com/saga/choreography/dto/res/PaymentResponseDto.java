package com.saga.choreography.dto.res;

import com.saga.choreography.entity.BaseEntity;
import lombok.Data;

@Data
public class PaymentResponseDto extends BaseEntity {
    private String id;
    private String orderId;
    private Double amount;
}

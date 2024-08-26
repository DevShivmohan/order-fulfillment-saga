package com.saga.choreography.dto.res;

import com.saga.choreography.dto.ShipmentStatus;
import com.saga.choreography.entity.BaseEntity;
import lombok.Data;

@Data
public class ShipmentResponseDto extends BaseEntity {
    private String orderId;
    private ShipmentStatus status;
}

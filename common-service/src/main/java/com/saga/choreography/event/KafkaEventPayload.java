package com.saga.choreography.event;

import com.saga.choreography.dto.res.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEventPayload {
    private OrderResponseDto payload;
    private KafkaEventType kafkaEventType;
}

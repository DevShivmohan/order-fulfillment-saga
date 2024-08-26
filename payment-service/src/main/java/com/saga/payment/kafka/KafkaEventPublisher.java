package com.saga.payment.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.choreography.dto.res.OrderResponseDto;
import com.saga.choreography.event.KafkaEventPayload;
import com.saga.choreography.event.KafkaEventType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishKafkaEvent(final String topicName, final KafkaEventType eventType, final OrderResponseDto orderResponseDto) {
        log.info("Publishing the kafka event {} and payload {} and topic name {}", eventType, orderResponseDto, topicName);
        try {
            kafkaTemplate.send(topicName, objectMapper.writeValueAsString(KafkaEventPayload.builder().kafkaEventType(eventType).payload(orderResponseDto).build()));
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e.getMessage());
        }
    }
}

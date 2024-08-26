package com.saga.choreography.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.choreography.event.KafkaEventPayload;
import com.saga.choreography.event.KafkaEventType;
import com.saga.choreography.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaEventConsumer {
    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 20000),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(topics = "shipment-placed", groupId = "order-transactions")
    public void listenKafkaEvent(@Payload String message,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws JsonProcessingException {
        final KafkaEventPayload kafkaEventPayload = objectMapper.readValue(message, KafkaEventPayload.class);
        if (kafkaEventPayload.getKafkaEventType() == KafkaEventType.SHIPMENT_PLACED) {
            orderService.completeOrder(kafkaEventPayload.getPayload());
        }
        if (kafkaEventPayload.getKafkaEventType() == KafkaEventType.SHIPMENT_CANCELED) {
            orderService.failOrder(kafkaEventPayload.getPayload());
        }
        log.info("Transaction completed with order id {}", kafkaEventPayload.getPayload().getId());
    }
}

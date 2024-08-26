package com.saga.shipment.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.choreography.constants.KafkaEventConstants;
import com.saga.choreography.event.KafkaEventPayload;
import com.saga.choreography.event.KafkaEventType;
import com.saga.shipment.service.ShipmentService;
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
    private final KafkaEventPublisher kafkaEventPublisher;
    private final ShipmentService shipmentService;

    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 20000),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(topics = "payment-captured", groupId = "order-transactions")
    public void listenKafkaEventForOrderInitiated(@Payload String message,
                                                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws JsonProcessingException {
        final KafkaEventPayload kafkaEventPayload = objectMapper.readValue(message, KafkaEventPayload.class);
        try {
            if (kafkaEventPayload.getKafkaEventType() == KafkaEventType.PAYMENT_CAPTURED) {
                shipmentService.placeShipment(kafkaEventPayload.getPayload());
                kafkaEventPublisher.publishKafkaEvent(KafkaEventConstants.KAFKA_TOPIC_SHIPMENT_PLACED, KafkaEventType.SHIPMENT_PLACED, kafkaEventPayload.getPayload());
            }
        } catch (Exception e) {
            log.error(e.toString());
            shipmentService.cancelShipment(kafkaEventPayload.getPayload());
            kafkaEventPublisher.publishKafkaEvent(KafkaEventConstants.KAFKA_TOPIC_SHIPMENT_CANCELLED, KafkaEventType.SHIPMENT_CANCELED, kafkaEventPayload.getPayload());
        }
    }
}

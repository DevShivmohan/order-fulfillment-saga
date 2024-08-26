package com.saga.payment.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saga.choreography.constants.KafkaEventConstants;
import com.saga.choreography.event.KafkaEventPayload;
import com.saga.choreography.event.KafkaEventType;
import com.saga.payment.service.PaymentService;
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
    private final PaymentService paymentService;
    private final KafkaEventPublisher kafkaEventPublisher;

    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 20000),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(topics = "order-initiated", groupId = "order-transactions")
    public void listenKafkaEventForOrderInitiated(@Payload String message,
                                                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws JsonProcessingException {
        final KafkaEventPayload kafkaEventPayload = objectMapper.readValue(message, KafkaEventPayload.class);
        try {
            if (kafkaEventPayload.getKafkaEventType() == KafkaEventType.ORDER_INITIATED) {
                paymentService.makePayment(kafkaEventPayload.getPayload());
                kafkaEventPublisher.publishKafkaEvent(KafkaEventConstants.KAFKA_TOPIC_PAYMENT_CAPTURED,KafkaEventType.PAYMENT_CAPTURED,kafkaEventPayload.getPayload());
            }
        }catch (Exception e){
            log.error(e.toString());
            paymentService.reversePayment(kafkaEventPayload.getPayload());
            kafkaEventPublisher.publishKafkaEvent(KafkaEventConstants.KAFKA_TOPIC_PAYMENT_FAILED,KafkaEventType.PAYMENT_FAILED,kafkaEventPayload.getPayload());
        }
    }



    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 20000),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(topics = "shipment-cancelled", groupId = "order-transactions")
    public void listenKafkaEventForShipmentCancelled(@Payload String message,
                                                  @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) throws JsonProcessingException {
        final KafkaEventPayload kafkaEventPayload = objectMapper.readValue(message, KafkaEventPayload.class);
        if (kafkaEventPayload.getKafkaEventType() == KafkaEventType.SHIPMENT_CANCELED) {
            paymentService.reversePayment(kafkaEventPayload.getPayload());
            kafkaEventPublisher.publishKafkaEvent(KafkaEventConstants.KAFKA_TOPIC_PAYMENT_FAILED,KafkaEventType.PAYMENT_FAILED,kafkaEventPayload.getPayload());
        }
    }
}

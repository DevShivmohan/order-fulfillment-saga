package com.saga.choreography.constants;

public class KafkaEventConstants {
    public static final String KAFKA_TOPIC_ORDER_INITIATED="order-initiated";
    public static final String KAFKA_TOPIC_ORDER_FAILED="order-failed";
    public static final String KAFKA_TOPIC_PAYMENT_CAPTURED="payment-captured";
    public static final String KAFKA_TOPIC_PAYMENT_FAILED="payment-failed";
    public static final String KAFKA_TOPIC_SHIPMENT_CANCELLED="shipment-cancelled";
    public static final String KAFKA_TOPIC_SHIPMENT_PLACED="shipment-placed";
}

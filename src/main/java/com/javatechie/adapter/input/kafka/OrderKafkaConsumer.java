package com.javatechie.adapter.input.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.domain.dto.FoodOrder;
import com.javatechie.domain.port.input.PlaceOrderUsecasePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Example Kafka input adapter. Not active by default.
 * Add {@code spring-kafka}, enable the {@code kafka} profile, and uncomment {@code @KafkaListener} to use.
 */
@Component
@Profile("kafka")
public class OrderKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderKafkaConsumer.class);

    private final PlaceOrderUsecasePort placeOrderUseCasePort;
    private final ObjectMapper objectMapper;

    public OrderKafkaConsumer(PlaceOrderUsecasePort placeOrderUseCasePort, ObjectMapper objectMapper) {
        this.placeOrderUseCasePort = placeOrderUseCasePort;
        this.objectMapper = objectMapper;
    }

    // @KafkaListener(topics = "food-order-topic", groupId = "order-group")
    public void consume(String message) throws JsonProcessingException {
        FoodOrder order = objectMapper.readValue(message, FoodOrder.class);
        placeOrderUseCasePort.placeOrder(order);
        log.info("Kafka input adapter: order {} placed", order.getOrderId());
    }
}

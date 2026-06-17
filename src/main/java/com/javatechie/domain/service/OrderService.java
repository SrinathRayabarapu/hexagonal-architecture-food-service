package com.javatechie.domain.service;

import com.javatechie.domain.dto.FoodOrder;
import com.javatechie.domain.port.input.PlaceOrderUsecasePort;
import com.javatechie.domain.port.input.TrackOrderUsecasePort;
import com.javatechie.domain.port.output.OrderRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Domain service implementing input ports and delegating persistence to an output port.
 * An intermediate adapter between ports and this service is optional for larger systems.
 */
public class OrderService implements PlaceOrderUsecasePort, TrackOrderUsecasePort {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepositoryPort orderRepositoryPort;

    public OrderService(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public void placeOrder(FoodOrder order) {
        order.setStatus("ORDER PLACED");
        log.info("Domain service: order {} placed", order.getOrderId());
        orderRepositoryPort.saveOrder(order);
    }

    @Override
    public String trackOrder(String orderId) {
        log.info("Domain service: tracking order {}", orderId);
        return orderRepositoryPort.findById(orderId);
    }
}

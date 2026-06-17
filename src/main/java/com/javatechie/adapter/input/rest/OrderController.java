package com.javatechie.adapter.input.rest;

import com.javatechie.domain.dto.FoodOrder;
import com.javatechie.domain.port.input.PlaceOrderUsecasePort;
import com.javatechie.domain.port.input.TrackOrderUsecasePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final PlaceOrderUsecasePort placeOrderUsecasePort;
    private final TrackOrderUsecasePort trackOrderUsecasePort;

    public OrderController(PlaceOrderUsecasePort placeOrderUsecasePort, TrackOrderUsecasePort trackOrderUsecasePort) {
        this.placeOrderUsecasePort = placeOrderUsecasePort;
        this.trackOrderUsecasePort = trackOrderUsecasePort;
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody FoodOrder order) {
        log.info("REST input adapter: placing order {}", order.getOrderId());
        placeOrderUsecasePort.placeOrder(order);
        return ResponseEntity.ok("Order placed");
    }

    @GetMapping("/track/{orderId}")
    public ResponseEntity<String> trackOrder(@PathVariable String orderId) {
        log.info("REST input adapter: tracking order {}", orderId);
        return ResponseEntity.ok("Status: " + trackOrderUsecasePort.trackOrder(orderId));
    }
}

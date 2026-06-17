package com.javatechie.adapter.output.repository;

import com.javatechie.domain.dto.FoodOrder;
import com.javatechie.domain.port.output.OrderRepositoryPort;

/**
 * Reference stub for a MongoDB output adapter.
 * Not registered as a Spring bean — swap {@link com.javatechie.adapter.output.JpaOrderRepository}
 * with this class in configuration to demonstrate adapter replacement.
 */
public class MongoOrderRepository implements OrderRepositoryPort {

    @Override
    public void saveOrder(FoodOrder order) {
        throw new UnsupportedOperationException("MongoDB adapter is not wired in this project");
    }

    @Override
    public String findById(String orderId) {
        throw new UnsupportedOperationException("MongoDB adapter is not wired in this project");
    }
}

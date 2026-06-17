package com.javatechie.adapter.output;

import com.javatechie.adapter.output.entity.OrderEntity;
import com.javatechie.adapter.output.repository.SpringDataOrderRepository;
import com.javatechie.domain.dto.FoodOrder;
import com.javatechie.domain.port.output.OrderRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class JpaOrderRepository implements OrderRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(JpaOrderRepository.class);

    private final SpringDataOrderRepository repository;

    public JpaOrderRepository(SpringDataOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveOrder(FoodOrder order) {
        log.info("JPA output adapter: saving order {}", order.getOrderId());
        repository.save(mapToEntity(order));
    }

    @Override
    public String findById(String orderId) {
        OrderEntity entity = repository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found: " + orderId));
        log.info("JPA output adapter: found order {}", orderId);
        return mapToDomain(entity).getStatus();
    }

    private OrderEntity mapToEntity(FoodOrder order) {
        OrderEntity entity = new OrderEntity();
        entity.setOrderId(order.getOrderId());
        entity.setCustomerName(order.getCustomerName());
        entity.setRestaurantName(order.getRestaurantName());
        entity.setItem(order.getItem());
        entity.setStatus(order.getStatus());
        return entity;
    }

    private FoodOrder mapToDomain(OrderEntity entity) {
        FoodOrder order = new FoodOrder();
        order.setOrderId(entity.getOrderId());
        order.setCustomerName(entity.getCustomerName());
        order.setRestaurantName(entity.getRestaurantName());
        order.setItem(entity.getItem());
        order.setStatus(entity.getStatus());
        return order;
    }
}

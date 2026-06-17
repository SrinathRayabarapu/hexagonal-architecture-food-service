package com.javatechie.domain.port.output;

import com.javatechie.domain.dto.FoodOrder;

/**
 * Output port for notifying customers or downstream systems when an order changes.
 * Add an adapter (email, SMS, webhook) without changing domain logic.
 */
public interface OrderNotificationPort {

    void notifyOrderPlaced(FoodOrder order);
}

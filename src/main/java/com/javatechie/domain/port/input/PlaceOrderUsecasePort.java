package com.javatechie.domain.port.input;

import com.javatechie.domain.dto.FoodOrder;

public interface PlaceOrderUsecasePort {

    void placeOrder(FoodOrder order);
}

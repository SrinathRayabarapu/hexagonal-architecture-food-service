package com.javatechie.domain.service;

import com.javatechie.domain.dto.FoodOrder;
import com.javatechie.domain.port.output.OrderRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @InjectMocks
    private OrderService orderService;

    @Test
    void placeOrder_setsStatusAndPersists() {
        FoodOrder order = new FoodOrder();
        order.setOrderId("order-1");
        order.setCustomerName("Alex");
        order.setRestaurantName("Pizza Place");
        order.setItem("Margherita");

        orderService.placeOrder(order);

        ArgumentCaptor<FoodOrder> captor = ArgumentCaptor.forClass(FoodOrder.class);
        verify(orderRepositoryPort).saveOrder(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo("ORDER PLACED");
    }

    @Test
    void trackOrder_returnsStatusFromRepository() {
        when(orderRepositoryPort.findById("order-1")).thenReturn("ORDER PLACED");

        String status = orderService.trackOrder("order-1");

        assertThat(status).isEqualTo("ORDER PLACED");
    }
}
